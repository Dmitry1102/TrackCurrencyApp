package com.example.trackcurrencyapp.data.database.sort

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sort_currencies")
data class SortEntity(
   @PrimaryKey(autoGenerate = true)
   var id: Int? = null,
   val parameter: String,
   val value: Boolean,
)
