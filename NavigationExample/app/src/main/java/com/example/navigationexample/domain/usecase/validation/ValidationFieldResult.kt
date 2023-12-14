package com.example.navigationexample.domain.usecase.validation


data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)