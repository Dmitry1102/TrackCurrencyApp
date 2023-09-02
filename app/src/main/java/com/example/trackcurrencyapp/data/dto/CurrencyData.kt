package com.example.trackcurrencyapp.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyData(
    @SerializedName("base")
    val base: String?,

    @SerializedName("date")
    val date: String?,

    @SerializedName("rates")
    val rates: Map<String,Double>,

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("timestamp")
    val timestamp: Int?
)