package com.froxengine.booking.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class SportCenter(
    @SerializedName("_id") val id: String,
    val name: String,
    val image: String,
    val description: String,
    val address: String,
    val price: BigDecimal,
    val constraint: String,
    @SerializedName("__v") val version : Int
)