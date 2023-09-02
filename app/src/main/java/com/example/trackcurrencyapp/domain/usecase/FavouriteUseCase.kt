package com.example.trackcurrencyapp.domain.usecase

import com.example.trackcurrencyapp.data.database.favourite.FavouriteEntity
import com.example.trackcurrencyapp.data.repository.IFavouriteRepository
import javax.inject.Inject

interface IFavouriteUseCase {

   suspend fun getAllFavoriteCurrencies(): List<String>

   suspend fun getFavouriteByCurrency(currency: String): List<FavouriteEntity>

   suspend fun getAmountOfCurrencies(): Int

   suspend fun insertFavourite(currency: FavouriteEntity)

   suspend fun deleteFavourite(currency: FavouriteEntity)

   suspend fun deleteAllFavoriteCurrencies()
}

class FavouriteUseCase @Inject constructor(
   private val repository: IFavouriteRepository
): IFavouriteUseCase {

   override suspend fun getAllFavoriteCurrencies(): List<String> {
      return repository.getAllFavoriteCurrencies()
   }

   override suspend fun getFavouriteByCurrency(currency: String): List<FavouriteEntity> {
      return repository.getFavouriteByCurrency(currency)
   }

   override suspend fun getAmountOfCurrencies(): Int {
      return repository.getAmountOfCurrencies()
   }

   override suspend fun insertFavourite(currency: FavouriteEntity){
      repository.insertFavourite(currency)
   }

   override suspend fun deleteFavourite(currency: FavouriteEntity) {
      repository.deleteFavourite(currency)
   }

   override suspend fun deleteAllFavoriteCurrencies() {
      repository.deleteAllFavoriteCurrencies()
   }
}