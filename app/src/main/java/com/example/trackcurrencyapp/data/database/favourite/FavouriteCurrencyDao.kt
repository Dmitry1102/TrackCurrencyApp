package com.example.trackcurrencyapp.data.database.favourite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteCurrencyDao {

   @Query("SELECT * FROM favourite_currencies")
   suspend fun getAllFavoriteCurrencies(): List<FavouriteEntity>

   @Query("SELECT * FROM favourite_currencies WHERE currency = :currency")
   suspend fun getFavouriteByCurrency(currency: String): List<FavouriteEntity>

   @Query("SELECT COUNT(*) FROM favourite_currencies")
   suspend fun getAmountOfCurrencies(): Int

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertFavourite(currency: FavouriteEntity)

   @Delete
   suspend fun deleteFavourite(currency: FavouriteEntity)

   @Query("DELETE FROM favourite_currencies")
   suspend fun deleteAllFavoriteCurrencies()

}