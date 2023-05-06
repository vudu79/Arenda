package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class DateLongValidation @Inject constructor() {

    fun execute(date: Long): ValidationResult {

        if (date==0L) {
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