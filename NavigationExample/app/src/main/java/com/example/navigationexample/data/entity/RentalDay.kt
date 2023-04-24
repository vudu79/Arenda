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

    @ColumnInfo(name = "client_name")
    var clientName: String = ""

    @ColumnInfo(name = "appatment_name")
    var appatmentName: String = ""

    constructor(epochDay: Long, clientColor: Int, clientName: String, appatmentName: String) {
        this.epochDay = epochDay
        this.clientColor = clientColor
        this.clientName = clientName
        this.appatmentName = appatmentName
    }
}