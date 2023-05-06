package com.example.navigationexample.domain.usecase.validation.validators

import android.util.Patterns
import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class PhoneValidation @Inject constructor(){

    fun execute(phone: String): ValidationResult {
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

//        if (Patterns.PHONE.matcher(phone).matches()) {
//            return ValidationResult(
//                successful = false,
//                errorMessage = "Не корректный номер телефона"
//            )
//        }

        return ValidationResult(
            successful = true
        )
    }
}