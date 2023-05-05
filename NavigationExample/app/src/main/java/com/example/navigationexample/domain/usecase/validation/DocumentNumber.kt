package com.example.navigationexample.domain.usecase.validation

class DocumentNumber {

    fun execute(documentNumber: String): ValidationResult {
        val hasOnliDigits = documentNumber.all { it.isDigit() }

        if (documentNumber.isNotEmpty() && !hasOnliDigits && documentNumber.length != 10) {
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