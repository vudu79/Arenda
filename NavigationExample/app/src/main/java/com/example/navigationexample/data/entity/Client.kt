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
    var members: String?= ""

    @ColumnInfo(name = "in_date")
    var inDate: Long? = 0

    @ColumnInfo(name = "out_date")
    var outDate: Long? = 0

    @ColumnInfo(name = "totalDays")
    var totalDays: Int? = 0

    @ColumnInfo(name = "prepayment")
    var prepayment: Int? = 0

    @ColumnInfo(name = "payment")
    var payment: Int? = 0

    @ColumnInfo(name = "sity")
    var sity: String? = ""

    @NonNull
    @ColumnInfo(name = "appatment_name")
    var appatment_name: Int = 0

    constructor() {}

    constructor(name: String, phone: String, inDate: Long?, outDate: Long?, appatment_name: Int) {
        this.name = name
        this.phone = phone
        this.inDate = inDate
        this.outDate = outDate
        this.appatment_name = appatment_name
    }

    constructor(
        name: String,
        phone: String,
        members: String?,
        inDate: Long?,
        outDate: Long?,
        totalDays: Int?,
        prepayment: Int?,
        payment: Int?,
        sity: String?,
        appatment_name: Int
    ) {
        this.name = name
        this.phone = phone
        this.members = members
        this.inDate = inDate
        this.outDate = outDate
        this.totalDays = totalDays
        this.prepayment = prepayment
        this.payment = payment
        this.sity = sity
        this.appatment_name = appatment_name
    }

    constructor(
        name: String,
        phone: String,
        inDate: String,
        outDate: String,
        appatment_name: String
    )

}