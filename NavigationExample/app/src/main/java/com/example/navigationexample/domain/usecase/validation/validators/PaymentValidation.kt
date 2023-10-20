package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.domain.usecase.validation.ValidationResult
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class PaymentValidation @Inject constructor() {
    fun execute(state: ValidationFormState): ValidationResult {

        val payment = state.payment.trim()
        val prePayment = state.prePayment.trim()
        val paymentInt = payment.trim().toInt()
        val prePaymentInt = prePayment.trim().toInt()

        val inDate = state.dateInLong ?: 0L
        val outDate = state.dateOutLong ?: 0L
        val ldInDate = LocalDate.ofEpochDay(inDate)
        val ldOutDate = LocalDate.ofEpochDay(outDate)
        val totalDays = ChronoUnit.DAYS.between(ldInDate, ldOutDate).toInt()
        val totalCoast = paymentInt * totalDays

        val hasOnliDigits = payment.all { it.isDigit() }

        if (payment.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        if (prePaymentInt > totalCoast) {
            return ValidationResult(
                successful = false,
                errorMessage = "Залог больше стоимости проживания"
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