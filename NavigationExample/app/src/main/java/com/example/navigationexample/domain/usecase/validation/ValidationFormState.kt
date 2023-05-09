package com.example.navigationexample.domain.usecase.validation

import androidx.compose.ui.graphics.Color
import com.example.navigationexample.constants.Constans

data class ValidationFormState(
    var firstName: String = "",
    var firstNameError: String? = null,
    var secondName: String? = "",
    var secondNameError: String? = null,
    var lastName: String? = "",
    var lastNameError: String? = null,
    var phone: String = "",
    var phoneError: String? = null,
    var documentNamber: String? = "",
    var documentNamberError: String? = null,
    var documentDitails: String? = "",
    var documentDitailsError: String? = null,
    var members: String = "",
    var membersError: String? = null,
    var dateOutString: String = "",
    var dateOutStringError: String? = null,
    var dateInString: String = "",
    var dateInStringError: String? = null,
    var dateOutLong: Long = 0L,
    var dateOutLongError: String? = null,
    var dateInLong: Long = 0L,
    var dateInLongError: String? = null,
    var prePayment: String = "",
    var prePaymentError: String? = null,
    var payment: String = "",
    var paymentError: String? = null,
    var transferInfo: String? = "",
    var transferInfoError: String? = null,
    var referer: String? = "",
    var refererError: String? = null,
    var color: Color = Constans.ClientColorsList.clientColorsList[0],
    var colorError: String? = null,
//    var appatmentName: String = ""
)