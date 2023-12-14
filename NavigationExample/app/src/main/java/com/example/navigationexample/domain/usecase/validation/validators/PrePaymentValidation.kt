package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.domain.usecase.validation.ValidationResult
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class PrePaymentValidation @Inject constructor() {
    fun execute(state: ValidationFormState): ValidationResult {

        val pricePerDay = state.pricePerDay.trim()
        val prePaymentPercent = state.prePaymentPercent.trim()
        val paymentInt = pricePerDay.trim().toInt()
        val prePaymentPercentInt = prePaymentPercent.trim().toInt()

        val inDate = state.dateInLong ?: 0L
        val outDate = state.dateOutLong ?: 0L
        val ldInDate = LocalDate.ofEpochDay(inDate)
        val ldOutDate = LocalDate.ofEpochDay(outDate)
        val totalDays = ChronoUnit.DAYS.between(ldInDate, ldOutDate).toInt()
        val totalCoast = paymentInt * totalDays
        val prePayment = totalCoast / 100 * prePaymentPercentInt
        val hasOnliDigits = prePaymentPercent.all { it.isDigit() }

        if (pricePerDay.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        if (prePayment > totalCoast) {
            return ValidationResult(
                successful = false,
                errorMessage = "Залог больше стоимости проживания"
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