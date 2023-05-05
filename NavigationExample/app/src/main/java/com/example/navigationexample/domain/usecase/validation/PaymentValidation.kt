package com.example.navigationexample.domain.usecase.validation

import android.util.Patterns

class PaymentValidation {

    fun execute(payment: String): ValidationResult {
        val hasOnliDigits = payment.all { it.isDigit() }

        if(payment.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }


        if (!hasOnliDigits || !payment.matches(Regex("""@"^[0-9]+${'$'}""""))) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле должно содержать только цифры"
            )
        }


        return ValidationResult(
            successful = true
        )
    }
}