package com.example.navigationexample.domain.usecase.validation

import androidx.lifecycle.MutableLiveData
import androidx.room.ColumnInfo

data class ValidationFormState(
    var firstName: String = "",
    var secondName: String? = "",
    var lastName: String? = "",
    var phone: String = "",
    var documentNamber: String? = "",
    var documentDitails: String? = "",
    var members: String? = "",
    var dateOutString: String = "",
    var dateInString: String = "",
    var dateOutLong: Long = 0L,
    var dateInLong: Long = 0L,
    var prepayment: String = "",
    var payment: String = "",
    var sity: String? = "",
    var appatmentName: String = ""
)