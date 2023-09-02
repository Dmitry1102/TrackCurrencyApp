package com.example.trackcurrencyapp.domain.model

data class Currency(
    val success: Boolean,
    val timestamp: Int,
    val date: String,
    val base: String,
    val rates: Map<String, Double>
)