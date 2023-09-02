package com.example.trackcurrencyapp.presentation.utility

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
   val main: CoroutineDispatcher
   val io: CoroutineDispatcher
   val default: CoroutineDispatcher
   val unconfined: CoroutineDispatcher

}