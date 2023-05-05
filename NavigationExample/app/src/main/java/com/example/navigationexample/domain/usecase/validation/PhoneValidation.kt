package com.example.navigationexample.domain.usecase.validation

import android.util.Patterns

class PhoneValidation {

    fun execute(phone: String): ValidationResult {
        val hasOnliDigits = phone.all { it.isDigit() }

        if (phone.isNullOrEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        if (!hasOnliDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле должно содержать только цифры"
            )
        }

        if (Patterns.PHONE.matcher(phone).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Не корректный номер телефона"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}