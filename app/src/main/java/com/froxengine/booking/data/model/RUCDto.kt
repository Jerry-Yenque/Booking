package com.froxengine.booking.data.model

data class RUCDto(
    val number: String,
    val name: String,
    val state: String,
    val homeState: String?,
    val ubigeo: String?,
    val streetType: String?,
    val street: String?,
    val zoneCode: String?,
    val zoneType: String?,
    val streetNumber: String?,
    val streetInterior: String?,
    val streetBatch: String?,
    val apartmentNumber: String?,
    val block: String?,
    val kilometer: String?,
    val department: String?,
    val province: String?,
    val district: String?,
    val address: String?,
    val updated: String?
)
