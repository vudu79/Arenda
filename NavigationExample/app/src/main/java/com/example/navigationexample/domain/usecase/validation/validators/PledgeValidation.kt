package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class PledgeValidation @Inject constructor() {
    fun execute(pledge: String): ValidationResult {

        val hasOnliDigits = pledge.all { it.isDigit() }

        if (pledge.isEmpty()) {
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

        return try {
            ValidationResult(
                successful = true
            )
        } catch (e: Exception) {

            ValidationResult(
                successful = false,
                errorMessage = "Не корректные данные"
            )
        }
    }
}