package com.example.trackcurrencyapp.data.repository

import com.example.trackcurrencyapp.data.service.IPopularService
import com.example.trackcurrencyapp.domain.mapper.PopularCurrencyMapper
import com.example.trackcurrencyapp.domain.model.Currency
import com.example.trackcurrencyapp.domain.model.Resource
import javax.inject.Inject

interface IPopularRepository {

   suspend fun getCurrency(): Resource<Currency>
   
   suspend fun getSpecialCurrencies(symbol: String): Resource<Currency>

   suspend fun getCurrencyByName(name: String): Resource<Currency>
}

class PopularRepositoryImpl @Inject constructor(
   private val service: IPopularService,
   private val currencyMapper: PopularCurrencyMapper
): IPopularRepository {

   override suspend fun getCurrency(): Resource<Currency> {
      return Resource {
         currencyMapper.map(currencyData = service.getCurrency())
      }
   }

   override suspend fun getSpecialCurrencies(symbol: String): Resource<Currency> {
      return Resource {
         currencyMapper.map(currencyData = service.getCurrencyBySymbols(symbol = symbol))
      }
   }

   override suspend fun getCurrencyByName(name: String): Resource<Currency> {
      return Resource {
         currencyMapper.map(currencyData = service.getCurrencyByName(name = name))
      }
   }
}