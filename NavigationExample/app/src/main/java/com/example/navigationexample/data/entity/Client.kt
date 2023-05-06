package com.example.navigationexample.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "clients")
class Client {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "clientId")
    var id: Int = 0


    @ColumnInfo(name = "first_name")
    var firstName: String = ""


    @ColumnInfo(name = "second_name")
    var secondName: String? = ""

    @ColumnInfo(name = "last_name")
    var lastName: String? = ""

    @ColumnInfo(name = "phone")
    var phone: String = ""

    @ColumnInfo(name = "document_number")
    var documentNamber: String? = ""

    @ColumnInfo(name = "document_ditails")
    var documentDitails: String? = ""

    @ColumnInfo(name = "members")
    var members: Int = 0

    @ColumnInfo(name = "in_date")
    var inDate: Long = 0

    @ColumnInfo(name = "out_date")
    var outDate: Long = 0

    @ColumnInfo(name = "prepayment")
    var prepayment: Int = 0

    @ColumnInfo(name = "payment")
    var payment: Int = 0

    @ColumnInfo(name = "client_color")
    var clientColor: Int = 0

    @ColumnInfo(name = "sity")
    var sity: String? = ""

    @ColumnInfo(name = "appatment_name")
    var appatment_name: String = ""

    constructor()
    constructor(
        firstName: String,
        phone: String,
        inDate: Long,
        outDate: Long,
        appatment_name: String
    ) {
        this.firstName = firstName
        this.phone = phone
        this.inDate = inDate
        this.outDate = outDate
        this.appatment_name = appatment_name
    }

    constructor(
        firstName: String,
        secondName: String?,
        lastName: String?,
        phone: String,
        documentNunber: String,
        documentDitails: String,
        inDate: Long,
        outDate: Long,
        members: Int,
        prepayment: Int,
        payment: Int,
        clientColor: Int,
        sity: String?,
        appatment_name: String
    ) {
        this.firstName = firstName
        this.secondName = secondName
        this.lastName = lastName
        this.phone = phone
        this.documentNamber = documentNunber
        this.documentDitails = documentDitails
        this.members = members
        this.inDate = inDate
        this.outDate = outDate
        this.prepayment = prepayment
        this.payment = payment
        this.clientColor = clientColor
        this.sity = sity
        this.appatment_name = appatment_name

    }
}