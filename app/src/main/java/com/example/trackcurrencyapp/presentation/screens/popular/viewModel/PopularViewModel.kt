package com.example.trackcurrencyapp.presentation.screens.popular.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackcurrencyapp.data.database.favourite.FavouriteEntity
import com.example.trackcurrencyapp.domain.model.Currency
import com.example.trackcurrencyapp.domain.model.CurrencyView
import com.example.trackcurrencyapp.domain.model.Resource
import com.example.trackcurrencyapp.domain.usecase.FavouriteUseCase
import com.example.trackcurrencyapp.domain.usecase.IPopularUseCase
import com.example.trackcurrencyapp.domain.usecase.SortUseCase
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.ALPHABET_ORDER
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.ERROR_MESSAGE
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.VALUE_ORDER
import com.example.trackcurrencyapp.presentation.utility.CurrencyFormat
import com.example.trackcurrencyapp.presentation.utility.DefaultCurrency
import com.example.trackcurrencyapp.presentation.utility.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
   private val popularUseCase: IPopularUseCase,
   private val favouriteUseCase: FavouriteUseCase,
   private val sortUseCase: SortUseCase,
   private val defaultCurrency: DefaultCurrency,
   private val dispatchers: DispatchersProvider,
   private val currencyFormat: CurrencyFormat
): ViewModel() {

   private val _eventStateFlow = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
   val eventStateFlow: StateFlow<CurrencyEvent> = _eventStateFlow.asStateFlow()

   sealed class CurrencyEvent {
      class Success(val currency: List<CurrencyView>): CurrencyEvent()
      class Failure(val message: String): CurrencyEvent()
      object Loading : CurrencyEvent()
      object Empty : CurrencyEvent()
   }

   fun getCurrencies() {
      viewModelScope.launch(dispatchers.io) {
         _eventStateFlow.value = CurrencyEvent.Loading
         handleCurrencyResponse()
      }
   }

   fun getCurrencyByName(name: String) {
      viewModelScope.launch(dispatchers.io) {
         _eventStateFlow.value = CurrencyEvent.Loading

         val response = popularUseCase.getCurrencyByName(name = name)
         val currencyResponse = response.data ?: defaultCurrency.getDefaultCurrency()
         val errorMessage = response.message ?: ERROR_MESSAGE

         when (response) {
            is Resource.Success -> onSuccess(currencyResponse)
            is Resource.Failure -> _eventStateFlow.value = CurrencyEvent.Failure(errorMessage)
         }
      }
   }

   fun editFavouriteCurrencies(currency: String) {
      viewModelScope.launch(dispatchers.io) {
         val currentFavoriteCurrencies = favouriteUseCase.getFavouriteByCurrency(currency)
         if (currentFavoriteCurrencies.isNotEmpty()) {
            currentFavoriteCurrencies.forEach {
               favouriteUseCase.deleteFavourite(it)
            }

         } else {
            val favoriteCurrency = FavouriteEntity(currency = currency)
            favouriteUseCase.insertFavourite(favoriteCurrency)
         }
         handleCurrencyResponse()
      }
   }

   private suspend fun onSuccess(data: Currency?) {
      val currency = data ?: defaultCurrency.getDefaultCurrency()
      val sortCurrencies = sortUseCase.getAllSortCurrencies()

      val currencyViewList = currencyFormat.transform(
            currencies = currency,
            favouriteCurrencyList = favouriteUseCase.getAllFavoriteCurrencies(),
            isAlphabet = checkSort(ALPHABET_ORDER, sortCurrencies),
            increase = checkSort(VALUE_ORDER, sortCurrencies)
         )

      _eventStateFlow.value = CurrencyEvent.Success(currencyViewList.currencyList)
   }

   private suspend fun handleCurrencyResponse() {
      val currencyResponse = popularUseCase.getCurrency()
      val errorMessage = currencyResponse.message ?: ERROR_MESSAGE

      when (currencyResponse) {
         is Resource.Success -> onSuccess(currencyResponse.data)
         is Resource.Failure -> _eventStateFlow.value = CurrencyEvent.Failure(errorMessage)
      }
   }

   private fun checkSort(typeSort: String, sortMap: Map<String,Boolean>): Boolean {
      val value = sortMap[typeSort]
      return value == null || value
   }
}


