package com.metes.worthit.core.domain.usecase

import com.metes.worthit.core.domain.entity.Item
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.UnexpectedError
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.utils.Result
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class GetItemByIdUseCase @Inject constructor(
    private val itemsRepository: ItemsRepository,
) {
    suspend operator fun invoke(itemId: Int): Result<Item, List<Error>> {
        return try {
            val item = itemsRepository.getItemById(itemId)
            Result.Success(item)
        } catch (c: CancellationException) {
            throw c
        } catch (_: IllegalStateException) {
            Result.Error(listOf(BusinessError.ItemNotFound))
        } catch (e: Exception) {
            Result.Error(listOf(UnexpectedError(e)))
        }
    }
}
