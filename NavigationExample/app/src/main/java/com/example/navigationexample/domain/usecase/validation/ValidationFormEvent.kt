package com.example.navigationexample.domain.usecase.validation

import androidx.compose.ui.graphics.Color

sealed class ValidationFormEvent {
    data class StatusChanged(val status: String) : ValidationFormEvent()
    data class FirstNameChanged(val firstName: String) : ValidationFormEvent()
    data class SecondNameChanged(val secondName: String) : ValidationFormEvent()
    data class LastNameChanged(val lastName: String) : ValidationFormEvent()
    data class PhoneChanged(val phone: String) : ValidationFormEvent()
    data class DocumentNamberChanged(val documentNamber: String) : ValidationFormEvent()
    data class DocumentDitailsChanged(val documentDitails: String) : ValidationFormEvent()
    data class MembersChanged(val members: String) : ValidationFormEvent()
    data class InStringDateChanged(val inDateString: String) : ValidationFormEvent()
    data class OutStringDateChanged(val outDateString: String) : ValidationFormEvent()
    data class InLongDateChanged(val inDateLong: Long) : ValidationFormEvent()
    data class OutLongDateChanged(val outDateLong: Long) : ValidationFormEvent()
    data class PrepaymentChanged(val prepayment: String) : ValidationFormEvent()
    data class PaymentChanged(val payment: String) : ValidationFormEvent()
    data class transferInfoChanged(val transferInfo: String) : ValidationFormEvent()
    data class refererChanged(val referer: String) : ValidationFormEvent()
    data class ColorChanged(val color: Color) : ValidationFormEvent()
    data class onSubmitInsert(val apartmentName: String) : ValidationFormEvent()
    data class onSubmitUpdate(val apartmentName: String) : ValidationFormEvent()
}


sealed class ValidatAllFieldsResultEvent{
    object InsertSuccess: ValidatAllFieldsResultEvent()
    object UpdateSuccess: ValidatAllFieldsResultEvent()
    object UpdateWrong: ValidatAllFieldsResultEvent()
}
