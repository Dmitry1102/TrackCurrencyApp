package com.example.trackcurrencyapp.presentation.utility

import com.example.trackcurrencyapp.domain.model.Currency
import com.example.trackcurrencyapp.domain.model.CurrencyView
import com.example.trackcurrencyapp.domain.model.CurrencyViewList

object CurrencyFormat {

   fun transform(
      currencies: Currency,
      favouriteCurrencyList: List<String>,
      isAlphabet: Boolean,
      increase: Boolean
   ): CurrencyViewList {
      val currencyViewList = currencies.rates.toList().map { value ->
         val currency= value.first
         val price = value.second
         val isFavourite = favouriteCurrencyList.contains(currency)
         CurrencyView(
            currency = currency,
            value = price,
            favourite = isFavourite
         )
      }

      currencyViewList.sortList(isAlphabet = isAlphabet, increase = increase)

      return CurrencyViewList(currencyList = currencyViewList)
   }

   fun transformFavourites(
      currency: Currency,
      isAlphabet: Boolean,
      increase: Boolean,
   ): CurrencyViewList {
      val currencyViewList = currency.rates.toList().map { currencyWithValue ->
            CurrencyView(
               currency = currencyWithValue.first,
               value = currencyWithValue.second,
               favourite = true,
            )
         }

      currencyViewList.sortList(isAlphabet = isAlphabet, increase = increase)

      return CurrencyViewList(currencyList = currencyViewList)
   }

   fun transformFavouritesByItem(
      currency: Currency,
      favouriteCurrencyList: List<String>,
      isAlphabet: Boolean,
      increase: Boolean,
   ): CurrencyViewList {
      val currencyViewList = currency.rates.toList().filter {
         favouriteCurrencyList.contains(it.first)
      }.map {
            currencyWithValue ->
         CurrencyView(
            currency = currencyWithValue.first,
            value = currencyWithValue.second,
            favourite = true
         )
      }

      currencyViewList.sortList(isAlphabet = isAlphabet, increase = increase)

      return CurrencyViewList(currencyList = currencyViewList)
   }

   private fun List<CurrencyView>.sortList(isAlphabet: Boolean, increase: Boolean): List<CurrencyView> {
      return if (isAlphabet) {
         this.sortedByCurrency(increase = increase)
      } else {
         this.sortedByValue(increase = increase)
      }
   }

   private fun List<CurrencyView>.sortedByCurrency(increase: Boolean): List<CurrencyView> {
      val result = this.sortedBy { item -> item.currency }
      return order(result = result, increase = increase)
   }

   private fun List<CurrencyView>.sortedByValue(increase: Boolean): List<CurrencyView> {
      val result = this.sortedBy { item -> item.value }
      return order(result = result, increase = increase)
   }

   private fun order(result: List<CurrencyView>, increase: Boolean): List<CurrencyView> {
      return if(increase) {
         result
      } else {
         result.reversed()
      }
   }
}