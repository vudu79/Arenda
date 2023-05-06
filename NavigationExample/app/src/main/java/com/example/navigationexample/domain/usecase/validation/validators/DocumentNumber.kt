package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class DocumentNumber @Inject constructor(){

    fun execute(documentNumber: String): ValidationResult {
        val hasOnliDigits = documentNumber.all { it.isDigit() }

        if (documentNumber.isNotEmpty() && !hasOnliDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле должно содержать только цифры"
            )
        }

        if (documentNumber.isNotEmpty() && documentNumber.length != 10) {
            return ValidationResult(
                successful = false,
                errorMessage = "Не корректные серия и номер"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}