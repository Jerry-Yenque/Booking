package com.froxengine.booking.data.model

data class ContactDto(
    val id: String?,
    val oid: String?,
    val name: String,
    val idType: IdentificationType,
    val idNumber: String,
    val email: String?
)

enum class IdentificationType(val code: String) {
    DNI("1"),
    RUC("6"),
    CE("4"),
    PASSPORT("7"),
    OTHER("0")
}
