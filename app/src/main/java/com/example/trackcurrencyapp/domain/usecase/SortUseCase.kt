package com.example.trackcurrencyapp.domain.usecase

import com.example.trackcurrencyapp.data.database.sort.SortEntity
import com.example.trackcurrencyapp.data.repository.ISortRepository
import javax.inject.Inject

interface ISortUseCase {

   suspend fun getAllSortCurrencies(): Map<String, Boolean>

   suspend fun getSortByParameter(parameter: String): List<SortEntity>

   suspend fun insert(sortEntity: SortEntity)

   suspend fun update(sortEntity: SortEntity)
}

class SortUseCase @Inject constructor(
   private val repository: ISortRepository
): ISortUseCase {

   override suspend fun getAllSortCurrencies(): Map<String, Boolean> {
      return repository.getAllSortCurrencies()
   }

   override suspend fun getSortByParameter(parameter: String): List<SortEntity> {
     return repository.getSortByParameter(parameter = parameter)
   }

   override suspend fun insert(sortEntity: SortEntity) {
      repository.insert(sortEntity = sortEntity)
   }

   override suspend fun update(sortEntity: SortEntity) {
      repository.update(sortEntity = sortEntity)
   }

}