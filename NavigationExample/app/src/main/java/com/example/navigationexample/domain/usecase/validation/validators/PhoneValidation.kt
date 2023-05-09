package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import java.util.regex.Pattern
import javax.inject.Inject

class PhoneValidation @Inject constructor(){

    fun execute(phonewithTrimed: String): ValidationResult {
        val phone = phonewithTrimed.trim()
        val hasOnliDigits = phone.all { it.isDigit() }

        if (phone.isEmpty()) {
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

//        if (phone.matches(Regex("""(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?"""))) {
        if (!phone.matches(Regex("""^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}${'$'}"""))) {
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