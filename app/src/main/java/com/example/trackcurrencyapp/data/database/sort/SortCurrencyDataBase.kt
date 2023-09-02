package com.example.trackcurrencyapp.data.database.sort

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SortEntity::class], version = 1, exportSchema = false)
abstract class SortCurrencyDataBase : RoomDatabase(){

   abstract fun sortCurrencyDao(): SortCurrencyDao

   companion object {

      @Volatile
      private var instance: SortCurrencyDataBase? = null

      fun getDatabase(context: Context): SortCurrencyDataBase =
         instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
               instance = it
            }
         }

      private fun buildDatabase(context: Context) =
         Room.databaseBuilder(context, SortCurrencyDataBase::class.java, "settings")
            .fallbackToDestructiveMigration()
            .build()

   }
}