package com.example.navigationexample.domain.usecase.validation

import androidx.lifecycle.MutableLiveData
import androidx.room.ColumnInfo

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
    var members: String? = "",
    var membersError: String? = null,
    var dateOutString: String = "",
    var dateOutStringError: String? = null,
    var dateInString: String = "",
    var dateInStringError: String? = null,
    var dateOutLong: Long = 0L,
    var dateOutLongError: String? = null,
    var dateInLong: Long = 0L,
    var dateInLongError: String? = null,
    var prepayment: String = "",
    var prepaymentError: String? = null,
    var payment: String = "",
    var paymentError: String? = null,
    var sity: String? = "",
    var sityError: String? = null,
//    var appatmentName: String = ""
)