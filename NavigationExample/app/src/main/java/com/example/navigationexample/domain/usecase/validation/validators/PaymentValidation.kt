package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class PaymentValidation @Inject constructor(){

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