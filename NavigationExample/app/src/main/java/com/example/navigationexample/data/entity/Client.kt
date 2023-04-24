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

    @NonNull
    @ColumnInfo(name = "name")
    var name: String = ""

    @NonNull
    @ColumnInfo(name = "phone")
    var phone: String= ""

    @ColumnInfo(name = "members")
    var members: Int?= 0

    @ColumnInfo(name = "in_date")
    var inDate: Long? = 0

    @ColumnInfo(name = "out_date")
    var outDate: Long? = 0

    @ColumnInfo(name = "prepayment")
    var prepayment: Int? = 0

    @ColumnInfo(name = "payment")
    var payment: Int? = 0

    @ColumnInfo(name = "client_color")
    var clientColor: Int = 0

    @ColumnInfo(name = "sity")
    var sity: String? = ""

    @NonNull
    @ColumnInfo(name = "appatment_name")
    var appatment_name: String=""

    constructor()
    constructor(
        name: String,
        phone: String,
        inDate: Long?,
        outDate: Long?,
        appatment_name: String
    ) {
        this.name = name
        this.phone = phone
        this.inDate = inDate
        this.outDate = outDate
        this.appatment_name = appatment_name
    }

    constructor(
        name: String,
        phone: String,
        inDate: Long,
        outDate: Long,
        members: Int,
        prepayment: Int,
        payment: Int,
        sity: String,
        clientColor: Int,
        appatment_name: String
    ){
        this.name = name
        this.phone = phone
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