package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.domain.usecase.validation.ValidationResult
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class OverPaymentValidation @Inject constructor() {
    fun execute(overPayment: String): ValidationResult {

        val hasOnliDigits = overPayment.all { it.isDigit() }

        if (overPayment.isEmpty()) {
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

        try {
            return ValidationResult(
                successful = true
            )
        } catch (e: Exception) {

            return ValidationResult(
                successful = false,
                errorMessage = "Не корректные данные"
            )
        }
    }
}