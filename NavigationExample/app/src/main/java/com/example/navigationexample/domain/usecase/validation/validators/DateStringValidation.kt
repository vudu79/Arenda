package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class DateStringValidation @Inject constructor() {

    fun execute(date: String): ValidationResult {

        if (date.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}