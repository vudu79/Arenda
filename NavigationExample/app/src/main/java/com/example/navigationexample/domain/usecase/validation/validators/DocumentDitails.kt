package com.example.navigationexample.domain.usecase.validation.validators

import com.example.navigationexample.domain.usecase.validation.ValidationResult
import javax.inject.Inject


class DocumentDitails @Inject constructor(){
    fun execute(documentDitails: String, mastHave: Boolean): ValidationResult {
        val specSimbols = "!#$%&'()*+-/:;<=>?@[]^_`{|}~".toList()
        val docList = documentDitails.toList()

        if (mastHave) {
            if (documentDitails.isEmpty() || documentDitails.length == 1) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Это обязательное поле"
                )
            }
        } else if (documentDitails.isNotEmpty()) {
            if (docList.intersect(specSimbols).size > 0) {
                return ValidationResult(
                    successful = false,
                    errorMessage = "Недопустимые символы"
                )
            }
        }
        return ValidationResult(
            successful = true
        )
    }
}