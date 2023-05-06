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

//        !payment.matches(Regex("""@"^[0-9]+${'$'}""""))

        if (!hasOnliDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле должно содержать только цифры"
            )
        }

        return try {
            val paymentInt = payment.toInt()
            ValidationResult(
                successful = true
            )
        }catch (e: Exception){
            ValidationResult(
                successful = false,
                errorMessage = "Не корректные данные"
            )
        }
    }
}