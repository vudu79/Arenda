package com.example.navigationexample.domain.usecase.validation

import androidx.compose.ui.graphics.Color
import com.example.navigationexample.constants.Constans
import com.example.navigationexample.domain.models.ClientStatus

data class ValidationFormState(
    var apartmentName: String? = "",
    var id: Long? = 0,
    var status: String = ClientStatus.intrasting,
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
    var overMembers: String = "",
    var overMembersError: String? = null,
    var dateOutString: String = "",
    var dateOutStringError: String? = null,
    var dateInString: String = "",
    var dateInStringError: String? = null,
    var dateOutLong: Long = 0L,
    var dateOutLongError: String? = null,
    var dateInLong: Long = 0L,
    var dateInLongError: String? = null,
    var prePaymentPercent: String = "",
    var prePaymentPercentError: String? = null,
    var prePayment: String = "0",
    var prePaymentError: String? = null,
    var pricePerDay: String = "0",
    var pricePerDayError: String? = null,
    var overPayment: String = "0",
    var overPaymentError: String? = null,
    var pledge: String = "0",
    var pledgeError: String? = null,

    var priceOfStay: String = "0",

    var completedPrePayment: String = "0",
    var prePaymentDone: Boolean = false,
    var dateOfCompletedPrePayment: Long = 0,
    var completedPrePaymentError: String? = null,

    var completedPayment: String = "0",
    var paymentDone: Boolean = false,
    var dateOfCompletedPayment: Long = 0,
    var completedPaymentError: String? = null,

    var completedPledge: String = "0",
    var dateOfCompletedPledge: Long = 0,
    var completedPledgeError: String? = null,


    var completedOverPayment: String = "0",
    var completedOverPaymentError: String? = null,

    var transferInfo: String? = "",
    var transferInfoError: String? = null,
    var referer: String? = "",
    var refererError: String? = null,
    var color: Color = Constans.ClientColorsList.clientColorsList[0],
    var colorError: String? = null,
//    var appatmentName: String = ""
)