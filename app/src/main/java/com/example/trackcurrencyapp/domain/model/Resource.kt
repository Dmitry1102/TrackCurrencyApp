package com.example.trackcurrencyapp.domain.model

import com.example.trackcurrencyapp.presentation.utility.Constants.Companion.ERROR_MESSAGE
import java.lang.Exception

sealed class Resource<T>(val data: T?, val message: String?) {
   class Success<T>(data: T) : Resource<T>(data = data , message = null)
   class Failure<T>(message: String) : Resource<T>(data = null, message = message)
}

inline fun <T> Resource(block: () -> T): Resource<T> = try {
   Resource.Success(block())
} catch (e: Exception) {
   Resource.Failure(e.message ?: ERROR_MESSAGE)
}