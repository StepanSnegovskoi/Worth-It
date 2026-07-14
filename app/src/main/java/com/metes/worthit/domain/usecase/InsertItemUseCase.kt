package com.metes.worthit.domain.usecase

import com.metes.worthit.domain.entity.Item
import com.metes.worthit.domain.repository.ItemsRepository
import com.metes.worthit.domain.repository.LocalMediaRepository
import com.metes.worthit.domain.utils.Result
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: LocalMediaRepository,
    private val clock: Clock,
) {
    suspend operator fun invoke(
        name: String,
        price: Long?,
        createdAt: Instant,
        boughtAt: LocalDate?,
        description: String,
        imageUriString: String?,
    ): Result<Unit, Exception> {
        if (name.isBlank()) {
            return Result.Error(IllegalArgumentException("Name can't be blank"))
        }

        if (boughtAt?.isAfter(LocalDate.now(clock)) == true) {
            return Result.Error(IllegalArgumentException("Bought date can't be in the future"))
        }

        val finalImagePath = if (imageUriString != null) {
            when (val imageResult = internalRepository.saveImage(imageUriString)) {
                is Result.Error -> return Result.Error(imageResult.error)
                is Result.Success -> imageResult.item
            }
        } else {
            null
        }

        val item = Item(
            name = name,
            price = price,
            createdAt = createdAt,
            boughtAt = boughtAt,
            description = description,
            imageLocalPath = finalImagePath
        )

        return try {
            if (itemsRepository.insertItem(item)) {
                Result.Success(Unit)
            } else {
                finalImagePath?.let { internalRepository.deleteImage(it) }
                Result.Error(Exception("Failed to insert item into database. Dao returned false."))
            }
        } catch (e: Exception) {
            finalImagePath?.let { internalRepository.deleteImage(it) }
            Result.Error(e)
        }
    }
}
