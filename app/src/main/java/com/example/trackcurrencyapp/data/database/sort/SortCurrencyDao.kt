package com.example.trackcurrencyapp.data.database.sort

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SortCurrencyDao {

   @Query("SELECT * FROM sort_currencies")
   suspend fun getAllSortCurrencies(): List<SortEntity>

   @Query("SELECT * FROM sort_currencies WHERE parameter = :parameter")
   suspend fun getSortByParameter(parameter: String): List<SortEntity>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(sortEntity: SortEntity)

   @Update
   suspend fun update(sortEntity: SortEntity)
}