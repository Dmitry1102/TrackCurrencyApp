package com.example.trackcurrencyapp.presentation.screens.favourite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackcurrencyapp.domain.model.Currency
import com.example.trackcurrencyapp.domain.model.CurrencyView
import com.example.trackcurrencyapp.domain.model.Resource
import com.example.trackcurrencyapp.domain.usecase.FavouriteUseCase
import com.example.trackcurrencyapp.domain.usecase.PopularUseCase
import com.example.trackcurrencyapp.domain.usecase.SortUseCase
import com.example.trackcurrencyapp.presentation.utility.Constants
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.ERROR_MESSAGE
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
class FavouriteViewModel @Inject constructor(
   private val favouriteUseCase: FavouriteUseCase,
   private val popularUseCase: PopularUseCase,
   private val sortUseCase: SortUseCase,
   private val dispatchers: DispatchersProvider,
   private val defaultCurrency: DefaultCurrency,
   private val currencyFormat: CurrencyFormat
): ViewModel() {

   private val _conversion = MutableStateFlow<FavouriteEvent>(FavouriteEvent.Empty)
   val conversion: StateFlow<FavouriteEvent> = _conversion.asStateFlow()

   private val _emptyStateFlow = MutableStateFlow(true)
   val emptyStateFlow: StateFlow<Boolean> = _emptyStateFlow.asStateFlow()

   sealed class FavouriteEvent {
      class Success(val currency: List<CurrencyView>): FavouriteEvent()
      class Failure(val message: String): FavouriteEvent()
      object Empty: FavouriteEvent()
   }

   fun getAllFavouriteCurrencies() {
      viewModelScope.launch(dispatchers.io) {
         _emptyStateFlow.emit(value = true)
         val listFavouriteCurrencies = favouriteUseCase.getAllFavoriteCurrencies()
         if(listFavouriteCurrencies.isNotEmpty()) {
            when(val response = popularUseCase.getSpecialCurrencies(special = listFavouriteCurrencies)) {
               is Resource.Success -> onSuccess(response.data)
               is Resource.Failure -> {
                  val errorMessage = response.message ?: ERROR_MESSAGE
                  _conversion.value = FavouriteEvent.Failure(errorMessage)
               }
            }
         }else {
            _emptyStateFlow.emit(value = true)
         }
      }
   }

   fun editFavoriteCurrencies(currency: String) {
      viewModelScope.launch(dispatchers.io) {
         val currentFavoriteCurrencies = favouriteUseCase.getFavouriteByCurrency(currency)
         if (currentFavoriteCurrencies.isNotEmpty()) {
            currentFavoriteCurrencies.forEach { currency ->
               if(currentFavoriteCurrencies.last() == currency){
                  _conversion.value = FavouriteEvent.Success(emptyList())
               }
               favouriteUseCase.deleteFavourite(currency)
            }
         }

         val countFavoriteCurrencies = favouriteUseCase.getAmountOfCurrencies()
         _emptyStateFlow.emit(countFavoriteCurrencies != 0)
         getAllFavouriteCurrencies()
      }
   }

   fun getCurrencyByName(name: String) {
      viewModelScope.launch(dispatchers.io) {
         val response = popularUseCase.getCurrencyByName(name = name)
         val currencyResponse = response.data ?: defaultCurrency.getDefaultCurrency()
         val errorMessage = response.message ?: ERROR_MESSAGE
         val sortCurrencies = sortUseCase.getAllSortCurrencies()

         val favouriteCurrencyList = favouriteUseCase.getAllFavoriteCurrencies()
         when (response) {
            is Resource.Success -> {
               val favouriteViewList = currencyFormat.transformFavouritesByItem(
                  currency = currencyResponse,
                  favouriteCurrencyList = favouriteCurrencyList,
                  isAlphabet = checkSort(Constants.ALPHABET_ORDER, sortCurrencies),
                  increase = checkSort(Constants.VALUE_ORDER, sortCurrencies)
               )

               _conversion.value = FavouriteEvent.Success(favouriteViewList.currencyList)
            }
            is Resource.Failure -> _conversion.value = FavouriteEvent.Failure(errorMessage)
         }
      }
   }

   fun deleteAllFavoriteCurrencies() {
      viewModelScope.launch(dispatchers.io) {
         favouriteUseCase.deleteAllFavoriteCurrencies()
         _conversion.value = FavouriteEvent.Success(emptyList())
      }
   }

   private suspend fun onSuccess(data: Currency?) {
      val currency = data ?: defaultCurrency.getDefaultCurrency()
      val sortCurrencies = sortUseCase.getAllSortCurrencies()

      val favouriteViewList = currencyFormat.transformFavourites(
            currency = currency,
            isAlphabet = checkSort(Constants.ALPHABET_ORDER, sortCurrencies),
            increase = checkSort(Constants.VALUE_ORDER, sortCurrencies)
      )

      _conversion.value = FavouriteEvent.Success(favouriteViewList.currencyList)
   }

   private fun checkSort(typeSort: String, sortMap: Map<String,Boolean>): Boolean {
      val value = sortMap[typeSort]
      return value == null || value
   }
}