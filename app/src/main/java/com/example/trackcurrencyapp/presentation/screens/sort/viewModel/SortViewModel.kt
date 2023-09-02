package com.example.trackcurrencyapp.presentation.screens.sort.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackcurrencyapp.data.database.sort.SortEntity
import com.example.trackcurrencyapp.domain.usecase.SortUseCase
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.ALPHABET_ORDER
import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.VALUE_ORDER
import com.example.trackcurrencyapp.presentation.utility.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val sortUseCase: SortUseCase,
    private val dispatchers: DispatchersProvider
): ViewModel() {

    private var _typeStateFlow = MutableStateFlow(true)
    val typeStateFlow: StateFlow<Boolean> = _typeStateFlow.asStateFlow()

    private var _orderStateFlow = MutableStateFlow(true)
    val orderStateFlow: StateFlow<Boolean> = _orderStateFlow.asStateFlow()

    fun setTypePositionState(state: Boolean) {
        _typeStateFlow.value = state
    }

    fun setOrderState(state: Boolean) {
        _orderStateFlow.value = state
    }

    fun getSortingOrder() {
        viewModelScope.launch(dispatchers.io) {
            val sort = sortUseCase.getAllSortCurrencies()
            fetchSort(sort[ALPHABET_ORDER], _typeStateFlow)
            fetchSort(sort[VALUE_ORDER], _orderStateFlow)
        }
    }

    private suspend fun fetchSort(value: Boolean?, stateFlow: MutableStateFlow<Boolean>) {
        stateFlow.emit(value == null || value)
    }

    fun setAlphabeticalSorting(isAlphabet: Boolean) {
        setSetting(ALPHABET_ORDER, isAlphabet)
    }

    fun setValueSorting(isIncrease: Boolean) {
        setSetting(VALUE_ORDER, isIncrease)
    }

    private fun setSetting(parameter: String, typeSort: Boolean) {
        viewModelScope.launch(dispatchers.io) {
            val sortEntity = sortUseCase.getSortByParameter(parameter).getOrNull(0)
            if (sortEntity != null) {
                val copiedSortEntity = sortEntity.copy(value = typeSort)
                sortUseCase.update(sortEntity = copiedSortEntity)
            } else {
                val addingSortEntity = SortEntity(parameter = parameter, value = typeSort)
                sortUseCase.insert(addingSortEntity)
            }
        }
    }
}