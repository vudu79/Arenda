package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.domain.usecase.validation.ValidationResult
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CompletedPaymentValidation @Inject constructor() {
    fun execute(state: ValidationFormState): ValidationResult {

        val completedPrePayment = state.completedPrePayment.trim()
        val prePaymentInt =
            if (state.prePayment.trim() == "") 0 else state.prePayment.trim().toInt()

        val completedPayment = state.completedPayment.trim()
        val paymentInt =
            if (state.priceOfStay.trim() == "") 0 else state.priceOfStay.trim().toInt()


        val hasOnlyDigits = completedPayment.all { it.isDigit() }

        if (completedPayment.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        if (!hasOnlyDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле должно содержать только цифры"
            )
        }
        val completedPaymentInt =
            if (state.completedPayment.trim() == "") 0 else state.completedPayment.trim()
                .toInt()
        val completedPrePaymentInt =
            if (state.completedPrePayment.trim() == "") 0 else state.completedPrePayment.trim()
                .toInt()


        if (completedPaymentInt > (paymentInt-completedPrePaymentInt)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Введите число от 0 до $prePaymentInt"
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