package com.example.composeex.models

data class Apatment(
val name: String,
val address: String?,
val square: Float?,
val cadastrNumber: String?
): java.io.Serializable {
    var plus: Float = 0.0f
    set(value) {
        field += value
    }
    var minus: Float = 0.0f
    set(value) {
        field += value
    }
    var debit: Float = 0.0f
    get() = this.plus - this.minus
}
