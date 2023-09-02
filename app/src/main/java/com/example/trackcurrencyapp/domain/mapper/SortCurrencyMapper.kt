package com.example.trackcurrencyapp.domain.mapper

import com.example.trackcurrencyapp.data.database.sort.SortEntity

class SortCurrencyMapper {
   fun map(sortEntity: List<SortEntity>): Map<String, Boolean> {
      return mutableMapOf<String, Boolean>().let { sortEntity.forEach { entity ->
            it[entity.parameter] = entity.value
         }
         it
      }
   }
}