package com.example.navigationexample.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.navigationexample.domain.models.ClientStatus


@Entity(tableName = "clients", indices = [Index(value = ["phone"],
    unique = true)])
class Client {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "clientId")
    var id: Int = 0

    @ColumnInfo(name = "status")
    var status: String = ClientStatus.waiting

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

    @ColumnInfo(name = "transfer_info")
    var transferInfo: String? = ""

    @ColumnInfo(name = "refer")
    var referer: String? = ""

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
        status: String,
        firstName: String,
        secondName: String?,
        lastName: String?,
        phone: String,
        documentNumber: String,
        documentDitails: String,
        inDate: Long,
        outDate: Long,
        members: Int,
        prepayment: Int,
        payment: Int,
        clientColor: Int,
        transferInfo: String?,
        referer: String?,
        appatment_name: String
    ){
        this.status = status
        this.firstName = firstName
        this.secondName = secondName
        this.lastName = lastName
        this.phone = phone
        this.documentNumber = documentNumber
        this.documentDitails = documentDitails
        this.members = members
        this.inDate = inDate
        this.outDate = outDate
        this.prepayment = prepayment
        this.payment = payment
        this.clientColor = clientColor
        this.transferInfo = transferInfo
        this.referer = referer
        this.appatment_name = appatment_name
    }

    constructor(
        id:Int,
        status: String,
        firstName: String,
        secondName: String?,
        lastName: String?,
        phone: String,
        documentNumber: String,
        documentDitails: String,
        inDate: Long,
        outDate: Long,
        members: Int,
        prepayment: Int,
        payment: Int,
        clientColor: Int,
        transferInfo: String?,
        referer: String?,
        appatment_name: String
    ){
        this.id = id
        this.status = status
        this.firstName = firstName
        this.secondName = secondName
        this.lastName = lastName
        this.phone = phone
        this.documentNumber = documentNumber
        this.documentDitails = documentDitails
        this.members = members
        this.inDate = inDate
        this.outDate = outDate
        this.prepayment = prepayment
        this.payment = payment
        this.clientColor = clientColor
        this.transferInfo = transferInfo
        this.referer = referer
        this.appatment_name = appatment_name
    }
}