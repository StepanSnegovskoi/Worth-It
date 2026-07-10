package com.metes.worthit.domain.usecase

import com.metes.worthit.domain.entity.Item
import com.metes.worthit.domain.repository.ItemsRepository
import com.metes.worthit.domain.repository.LocalMediaRepository
import com.metes.worthit.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertItemUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
    private val internalRepository: LocalMediaRepository,
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        imageUriString: String?
    ): Result<Unit, Exception> {
        if (imageUriString == null) {
            val item = Item(name = name, description = description, localPath = null)
            return try {
                if (itemsRepository.insertItem(item)) {
                    Result.Success(Unit)
                } else {
                    Result.Error(Exception("Failed to insert item into database. Dao returned false."))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

        return when (val imageResult = internalRepository.saveImage(imageUriString)) {
            is Result.Error -> {
                Result.Error(imageResult.error)
            }

            is Result.Success -> {
                val localImagePath = imageResult.item
                val item = Item(
                    name = name,
                    description = description,
                    localPath = localImagePath
                )

                try {
                    if (itemsRepository.insertItem(item)) {
                        Result.Success(Unit)
                    } else {
                        internalRepository.deleteImage(localImagePath)
                        Result.Error(Exception("Failed to insert item into database. Dao returned false."))
                    }
                } catch (e: Exception) {
                    internalRepository.deleteImage(localImagePath)
                    Result.Error(e)
                }
            }
        }
    }
}
