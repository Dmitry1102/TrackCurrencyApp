package com.example.trackcurrencyapp.domain.mapper

import com.example.trackcurrencyapp.data.database.favourite.FavouriteEntity

class FavouriteCurrencyMapper {

   fun map (favouriteCurrency: List<FavouriteEntity>): List<String> {
      return favouriteCurrency.map { it.currency }
   }
}