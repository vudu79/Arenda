package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import com.example.navigationexample.presentation.utils.hasWrongSimbols
import javax.inject.Inject

class MembersValidation@Inject constructor() {

    fun execute(memberswithTrimed: String): ValidationResult {
        val members = memberswithTrimed.trim()
        val hasOnlyDigits = members.all { it.isDigit() }

        if (members.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }

        if ( !hasOnlyDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле должно содержать только цифры"
            )
        }

        if (hasWrongSimbols(members)){
            return ValidationResult(
                successful = false,
                errorMessage = "Недопустимые символы в поле"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}