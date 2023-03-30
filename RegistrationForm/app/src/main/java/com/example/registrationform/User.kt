package com.example.registrationform

data class User(
    var login: String,
    var password: String,
    var name: String,
    var lastName: String
) : java.io.Serializable {
}