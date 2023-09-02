package com.example.trackcurrencyapp.data.database.favourite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class FavouriteCurrencyDataBase: RoomDatabase() {

   abstract fun favouriteCurrencyDao(): FavouriteCurrencyDao

   companion object {

      @Volatile
      private var instance: FavouriteCurrencyDataBase? = null

      fun getDatabase(context: Context): FavouriteCurrencyDataBase =
         instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
               instance = it
            }
         }

      private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            FavouriteCurrencyDataBase::class.java,"favourite_currencies")
            .fallbackToDestructiveMigration()
            .build()
   }
}