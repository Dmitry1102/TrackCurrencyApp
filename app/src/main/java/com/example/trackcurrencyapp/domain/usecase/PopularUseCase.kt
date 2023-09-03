package com.example.trackcurrencyapp.domain.usecase

import com.example.trackcurrencyapp.domain.model.Currency
import com.example.trackcurrencyapp.domain.model.Resource
import com.example.trackcurrencyapp.data.repository.IPopularRepository
import javax.inject.Inject

interface IPopularUseCase{

    suspend fun getCurrency(): Resource<Currency>

    suspend fun getFavouriteCurrenciesBySymbol(special: List<String>): Resource<Currency>

   suspend fun getCurrencyByName(name: String): Resource<Currency>
}

class PopularUseCase @Inject constructor(
   private val repository: IPopularRepository
): IPopularUseCase {

   override suspend fun getCurrency(): Resource<Currency> {
      return repository.getCurrency()
   }

   override suspend fun getFavouriteCurrenciesBySymbol(symbols: List<String>): Resource<Currency> {
      return repository.getFavouriteCurrenciesBySymbol(symbol = symbols.joinToString(separator = ","))
   }

   override suspend fun getCurrencyByName(name: String): Resource<Currency> {
      return repository.getCurrencyByName(name = name)
   }
}