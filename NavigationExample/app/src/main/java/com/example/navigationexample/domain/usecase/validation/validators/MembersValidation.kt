package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class MembersValidation@Inject constructor() {

    fun execute(members: String): ValidationResult {
        val hasOnlyDigits = members.all { it.isDigit() }

        if (members.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        if (members.isNotEmpty() && !hasOnlyDigits) {
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