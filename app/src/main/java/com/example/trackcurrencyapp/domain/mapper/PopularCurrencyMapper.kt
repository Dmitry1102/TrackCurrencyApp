package com.example.trackcurrencyapp.domain.mapper

import com.example.trackcurrencyapp.data.dto.CurrencyData
import com.example.trackcurrencyapp.domain.model.Currency

class PopularCurrencyMapper {

   fun map(currencyData: CurrencyData?): Currency {
      val value = currencyData ?: defaultCurrencyDto()

      val success = value.success ?: false
      val timeStamp = value.timestamp ?: 0
      val base = value.base ?: ""
      val date = value.date ?: ""
      val rates = value.rates

      return Currency(
         success = success,
         timestamp = timeStamp,
         base = base,
         date = date,
         rates = rates
      )
   }

   companion object {
      fun defaultCurrencyDto(): CurrencyData {
         return CurrencyData(
            success = true,
            timestamp = 100000,
            base = "EUR",
            date = "2023-08-31",
            rates = hashMapOf()
         )
      }
   }
}