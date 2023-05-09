package com.example.navigationexample.domain.usecase.validation.validators

import android.util.Log
import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class PaymentValidation @Inject constructor() {
    fun execute(paymentwithTrimed: String): ValidationResult {
        val payment = paymentwithTrimed.trim()
        val hasOnliDigits = payment.all { it.isDigit() }

        if (payment.isEmpty()) {
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

        try {
            val paymentInt = payment.trim().toInt()
            return ValidationResult(
                successful = true
            )
        } catch (e: Exception) {
            Log.d("myTag","sdfsdfsdfsdf ------- $e")
            return ValidationResult(

                successful = false,
                errorMessage = "Не корректные данные"
            )
        }
    }
}