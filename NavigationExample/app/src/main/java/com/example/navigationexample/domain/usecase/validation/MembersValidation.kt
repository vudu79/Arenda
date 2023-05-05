package com.example.navigationexample.domain.usecase.validation

import android.util.Patterns

class MembersValidation {

    fun execute(members: String): ValidationResult {
        val hasOnliDigits = members.all { it.isDigit() }

        if (members.isNotEmpty() && !hasOnliDigits) {
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