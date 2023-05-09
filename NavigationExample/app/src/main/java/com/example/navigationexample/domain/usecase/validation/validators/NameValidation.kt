package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import com.example.navigationexample.presentation.screens.common.hasWrongSimbols
import javax.inject.Inject

class NameValidation @Inject constructor(){

    fun execute(namewithTrimed: String, mastHave: Boolean): ValidationResult {
        val name = namewithTrimed.trim()

        val hasDigit = name.any { it.isDigit() }

        when(mastHave){
            true -> {
                if(name.isEmpty()) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Это обязательное поле"
                    )
                }
                if(name.length == 1) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Не может состоять из одного символа"
                    )
                }
                if(hasDigit) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Поле не может содержать цифры"
                    )
                }
                if (hasWrongSimbols(name)){
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Недопустимые символы в поле"
                    )
                }
            }
            false->{
                if(name.length == 1) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Не может состоять из одного символа"
                    )
                }

                if(hasDigit) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = "Поле не может содержать цифры"
                    )
                }
            }
        }

        return ValidationResult(
            successful = true
        )
    }
}