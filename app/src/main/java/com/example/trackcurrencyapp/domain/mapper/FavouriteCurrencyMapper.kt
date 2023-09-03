package com.example.trackcurrencyapp.domain.mapper

import com.example.trackcurrencyapp.data.database.favourite.FavouriteEntity

class FavouriteCurrencyMapper {

   fun map (favouriteCurrencyList: List<FavouriteEntity>): List<String> {
      return favouriteCurrencyList.map { it.currency }
   }
}