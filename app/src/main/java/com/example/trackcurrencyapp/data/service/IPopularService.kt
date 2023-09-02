package com.example.trackcurrencyapp.data.service

import com.example.trackcurrencyapp.BuildConfig
import com.example.trackcurrencyapp.data.dto.CurrencyData
import retrofit2.http.GET
import retrofit2.http.Query

interface IPopularService {

   @GET("latest")
   suspend fun getCurrency(
      @Query(ACCESS_KEY) accessKey: String = BuildConfig.TOKEN
   ): CurrencyData?

   @GET("latest")
   suspend fun getCurrencyBySymbols(
      @Query(SYMBOLS) symbol: String,
      @Query(ACCESS_KEY) accessKey: String = BuildConfig.TOKEN,
   ): CurrencyData

   @GET("latest")
   suspend fun getCurrencyByName(
      @Query(BASE) name: String,
      @Query(ACCESS_KEY) accessKey: String = BuildConfig.TOKEN,
   ): CurrencyData

   companion object {
      const val ACCESS_KEY = "access_key"
      const val SYMBOLS = "symbols"
      const val BASE = "base"
   }
}