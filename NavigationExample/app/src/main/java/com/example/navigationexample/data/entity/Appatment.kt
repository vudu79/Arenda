package com.example.navigationexample.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appatments")
class Appatment {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "appatmentId")
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "address")
    var address: String= ""

    @ColumnInfo(name = "type")
    var type: String= ""

    @ColumnInfo(name = "rental_period")
    var rentalPeriod: String= ""

    @ColumnInfo(name = "rooms")
    var numRooms: Int = 0

    @ColumnInfo(name = "beds")
    var numBeds: Int = 0

    @ColumnInfo(name = "square")
    var square: Float = 0f

    constructor() {}

    constructor(name: String, address: String, type: String, numRooms: Int, numBeds: Int, square: Float) {

        this.name = name
        this.address = address
        this.type = type
        this.numRooms = numRooms
        this.numBeds = numBeds
        this.square = square
    }

    constructor(name: String, address: String, type: String, rentalPeriod: String, numRooms: Int, numBeds: Int, square: Float){
        this.name = name
        this.address = address
        this.type = type
        this.rentalPeriod = rentalPeriod
        this.numRooms = numRooms
        this.numBeds = numBeds
        this.square = square
    }
}