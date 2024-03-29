package com.example.navigationexample.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.navigationexample.domain.models.ClientStatus


@Entity(
    tableName = "clients", indices = [Index(
        value = ["phone"],
        unique = true
    )]
)
class Client {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "client_id")
    var id: Long = 0

    @ColumnInfo(name = "status")
    var status: String = ClientStatus.intrasting

    @ColumnInfo(name = "first_name")
    var firstName: String = ""

    @ColumnInfo(name = "second_name")
    var secondName: String? = ""

    @ColumnInfo(name = "last_name")
    var lastName: String? = ""

    @ColumnInfo(name = "phone")
    var phone: String = ""

    @ColumnInfo(name = "document_number")
    var documentNumber: String? = ""

    @ColumnInfo(name = "document_ditails")
    var documentDitails: String? = ""

    @ColumnInfo(name = "members")
    var members: Int = 0

    @ColumnInfo(name = "over_members")
    var overMembers: Int = 0

    @ColumnInfo(name = "in_date")
    var inDate: Long = 0

    @ColumnInfo(name = "out_date")
    var outDate: Long = 0

    @ColumnInfo(name = "days_of_stay")
    var daysOfStay: Int = 0

    @ColumnInfo(name = "price_of_stay")
    var priceOfStay: Int = 0

    @ColumnInfo(name = "prepayment")
    var prePayment: Int = 0

    @ColumnInfo(name = "prepayment_percent")
    var prePaymentPercent: Int = 0

    @ColumnInfo(name = "price_per_day")
    var pricePerDay: Int = 0

    @ColumnInfo(name = "over_payment")
    var overPayment: Int = 0

    @ColumnInfo(name = "pledge")
    var pledge: Int = 0

    @ColumnInfo(name = "completed_pre_payment")
    var completedPrePayment: Int = 0

    @ColumnInfo(name = "pre_payment_done")
    var prePaymentDone: Boolean = false

    @ColumnInfo(name = "completed_payment")
    var completedPayment: Int = 0

    @ColumnInfo(name = "payment_done")
    var paymentDone: Boolean = false

    @ColumnInfo(name = "completed_pledge")
    var completedPledge: Int = 0

    @ColumnInfo(name = "completed_over_payment")
    var completedOverPayment: Int = 0


    @ColumnInfo(name = "date_completed_pre_payment")
    var dateOfCompletedPrePayment: Long = 0

    @ColumnInfo(name = "date_completed_payment")
    var dateOfCompletedPayment: Long = 0

    @ColumnInfo(name = "date_completed_pledge")
    var dateOfCompletedPledge: Long = 0


    @ColumnInfo(name = "client_color")
    var clientColor: Int = 0

    @ColumnInfo(name = "transfer_info")
    var transferInfo: String? = ""

    @ColumnInfo(name = "refer")
    var referer: String? = ""

    @ColumnInfo(name = "appatment_name")
    var appatmentName: String = ""

    constructor()
    constructor(
        firstName: String,
        phone: String,
        inDate: Long,
        outDate: Long,
        appatmentName: String
    ) {
        this.firstName = firstName
        this.phone = phone
        this.inDate = inDate
        this.outDate = outDate
        this.appatmentName = appatmentName
    }

    constructor(
        status: String,
        firstName: String,
        secondName: String?,
        lastName: String?,
        phone: String,
        documentNumber: String,
        documentDitails: String,
        inDate: Long,
        outDate: Long,
        daysOfStay:Int,
        priceOfStay:Int,
        members: Int,
        overMembers:Int,
        prePaymentPercent: Int,
        prePayment: Int,
        pricePerDay: Int,
        overPayment: Int,
        pledge:Int,

        completedPrePayment: Int,
        prePaymentDone: Boolean,
        completedPayment: Int,
        paymentDone: Boolean,
        completedPledge: Int,

        dateOfCompletedPrePayment: Long,
        dateOfCompletedPayment: Long,
        dateOfCompletedPledge: Long,

        completedOverPayment: Int,
        clientColor: Int,
        transferInfo: String?,
        referer: String?,
        appatmentName: String
    ) {
        this.status = status
        this.firstName = firstName
        this.secondName = secondName
        this.lastName = lastName
        this.phone = phone
        this.documentNumber = documentNumber
        this.documentDitails = documentDitails
        this.members = members
        this.overMembers = overMembers
        this.inDate = inDate
        this.outDate = outDate
        this.daysOfStay = daysOfStay
        this.priceOfStay = priceOfStay
        this.prePaymentPercent = prePaymentPercent
        this.prePayment = prePayment
        this.pricePerDay = pricePerDay
        this.overPayment = overPayment
        this.pledge = pledge

        this.completedPrePayment = completedPrePayment
        this.prePaymentDone = prePaymentDone
        this.completedPayment = completedPayment
        this.paymentDone = paymentDone
        this.completedPledge = completedPledge

        this.dateOfCompletedPrePayment = dateOfCompletedPrePayment
        this.dateOfCompletedPayment = dateOfCompletedPayment
        this.dateOfCompletedPledge = dateOfCompletedPledge

        this.completedOverPayment = completedOverPayment
        this.clientColor = clientColor
        this.transferInfo = transferInfo
        this.referer = referer
        this.appatmentName = appatmentName
    }

    constructor(
        id: Long,
        status: String,
        firstName: String,
        secondName: String?,
        lastName: String?,
        phone: String,
        documentNumber: String,
        documentDitails: String,
        inDate: Long,
        outDate: Long,
        daysOfStay:Int,
        priceOfStay:Int,
        members: Int,
        overMembers: Int,
        prePaymentPercent: Int,
        prePayment: Int,
        pricePerDay: Int,
        overPayment: Int,
        pledge: Int,

        completedPrePayment: Int,
        prePaymentDone:Boolean,
        completedPayment: Int,
        paymentDone:Boolean,
        completedPledge: Int,

        dateOfCompletedPrePayment: Long,
        dateOfCompletedPayment: Long,
        dateOfCompletedPledge: Long,

        completedOverPayment: Int,
        clientColor: Int,
        transferInfo: String?,
        referer: String?,
        appatmentName: String
    ) {
        this.id = id
        this.status = status
        this.firstName = firstName
        this.secondName = secondName
        this.lastName = lastName
        this.phone = phone
        this.documentNumber = documentNumber
        this.documentDitails = documentDitails
        this.members = members
        this.overMembers = overMembers
        this.inDate = inDate
        this.outDate = outDate
        this.daysOfStay = daysOfStay
        this.priceOfStay = priceOfStay
        this.prePaymentPercent = prePaymentPercent
        this.prePayment = prePayment
        this.pricePerDay = pricePerDay
        this.overPayment = overPayment
        this.pledge = pledge

        this.completedPrePayment = completedPrePayment
        this.prePaymentDone = prePaymentDone
        this.completedPayment = completedPayment
        this.paymentDone = paymentDone
        this.completedPledge = completedPledge

        this.dateOfCompletedPrePayment = dateOfCompletedPrePayment
        this.dateOfCompletedPayment = dateOfCompletedPayment
        this.dateOfCompletedPledge = dateOfCompletedPledge


        this.completedOverPayment = completedOverPayment
        this.clientColor = clientColor
        this.transferInfo = transferInfo
        this.referer = referer
        this.appatmentName = appatmentName
    }
}