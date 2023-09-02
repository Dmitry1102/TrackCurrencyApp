package com.example.trackcurrencyapp.data.repository

import com.example.trackcurrencyapp.data.database.sort.SortCurrencyDao
import com.example.trackcurrencyapp.data.database.sort.SortEntity
import com.example.trackcurrencyapp.domain.mapper.SortCurrencyMapper
import javax.inject.Inject

interface ISortRepository {

   suspend fun getAllSortCurrencies(): Map<String, Boolean>

   suspend fun getSortByParameter(parameter: String): List<SortEntity>

   suspend fun insert(sortEntity: SortEntity)

   suspend fun update(sortEntity: SortEntity)
}

class SortRepositoryImpl @Inject constructor(
   private val dao: SortCurrencyDao,
   private val sortMapper: SortCurrencyMapper
): ISortRepository {

   override suspend fun getAllSortCurrencies(): Map<String, Boolean> {
      return sortMapper.map(sortEntity = dao.getAllSortCurrencies())
   }

   override suspend fun getSortByParameter(parameter: String): List<SortEntity> {
      return dao.getSortByParameter(parameter = parameter)
   }

   override suspend fun insert(sortEntity: SortEntity) {
      dao.insert(sortEntity = sortEntity)
   }

   override suspend fun update(sortEntity: SortEntity) {
      dao.update(sortEntity = sortEntity)
   }


}