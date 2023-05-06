package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject

class NameValidation @Inject constructor(){

    fun execute(name: String, mastHave: Boolean): ValidationResult {
        val hasDigit = name.any { it.isDigit() }
        if(hasDigit) {
            return ValidationResult(
                successful = false,
                errorMessage = "Поле не может содержать цифры"
            )
        }

        if (mastHave){
            if(name.isEmpty()) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Это обязательное поле"
                )
            }
        }else{
            if(name.length == 1) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Не может состоять из одного символа"
                )
            }
        }
        if(name.isEmpty() || name.length == 1) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это обязательное поле"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}