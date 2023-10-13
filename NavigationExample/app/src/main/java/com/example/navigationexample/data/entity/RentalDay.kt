package com.example.navigationexample.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rentaldays")
class RentalDay {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "dayId")
    var dayId: Int = 0

    @ColumnInfo(name = "epoch_day")
    var epochDay: Long = 0

    @ColumnInfo(name = "client_color")
    var clientColor: Int = 0

    @ColumnInfo(name = "client_phone")
    var clientPhone: String = ""

    @ColumnInfo(name = "is_start_day")
    var isStartDay: Boolean = false

    @ColumnInfo(name = "is_end_day")
    var isEndDay: Boolean = false

    @ColumnInfo(name = "is_enable")
    var isEnable: Boolean = false

    @ColumnInfo(name = "appatment_name")
    var appatmentName: String = ""

    constructor(
        epochDay: Long,
        clientColor: Int,
        clientPhone: String,
        isStartDay: Boolean,
        isEndDay: Boolean,
        isEnable: Boolean,
        appatmentName: String
    ) {
        this.epochDay = epochDay
        this.clientColor = clientColor
        this.clientPhone = clientPhone
        this.isStartDay = isStartDay
        this.isEndDay = isEndDay
        this.isEnable = isEnable
        this.appatmentName = appatmentName
    }
}