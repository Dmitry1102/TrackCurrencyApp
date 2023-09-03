package com.example.trackcurrencyapp.data.repository

import com.example.trackcurrencyapp.data.database.favourite.FavouriteCurrencyDao
import com.example.trackcurrencyapp.data.database.favourite.FavouriteEntity
import com.example.trackcurrencyapp.domain.mapper.FavouriteCurrencyMapper
import javax.inject.Inject

interface IFavouriteRepository {

   suspend fun getAllFavoriteCurrencies(): List<String>

   suspend fun getFavouriteByCurrency(currency: String): List<FavouriteEntity>

   suspend fun getAmountOfCurrencies(): Int

   suspend fun insertFavourite(currency: FavouriteEntity)

   suspend fun deleteFavourite(currency: FavouriteEntity)

   suspend fun deleteAllFavoriteCurrencies()
}

class FavouriteRepositoryImpl @Inject constructor(
   private val dao: FavouriteCurrencyDao,
   private val mapper: FavouriteCurrencyMapper
): IFavouriteRepository {

   override suspend fun getAllFavoriteCurrencies(): List<String> {
      return mapper.map(favouriteCurrencyList = dao.getAllFavoriteCurrencies())
   }

   override suspend fun getFavouriteByCurrency(currency: String): List<FavouriteEntity> {
      return dao.getFavouriteByCurrency(currency = currency)
   }

   override suspend fun getAmountOfCurrencies(): Int {
      return dao.getAmountOfCurrencies()
   }

   override suspend fun insertFavourite(currency: FavouriteEntity) {
      dao.insertFavourite(currency = currency)
   }

   override suspend fun deleteFavourite(currency: FavouriteEntity) {
      dao.deleteFavourite(currency = currency)
   }

   override suspend fun deleteAllFavoriteCurrencies() {
      dao.deleteAllFavoriteCurrencies()
   }
}