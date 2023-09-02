package com.example.trackcurrencyapp.presentation.utility

import com.example.trackcurrencyapp.domain.model.Currency

class DefaultCurrency {

   fun getDefaultCurrency(): Currency {
      return Currency(
         success = true,
         timestamp = 100000,
         base = "EUR",
         date = "2023-08-31",
         rates = hashMapOf()
      )
   }


}