package com.example.trackcurrencyapp.data.database.favourite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_currencies")
data class FavouriteEntity(
   @PrimaryKey(autoGenerate = true)
   var id: Int? = null,
   val currency: String,
)