package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class MembersValidation@Inject constructor() {

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