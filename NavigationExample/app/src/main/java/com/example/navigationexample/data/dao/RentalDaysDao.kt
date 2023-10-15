package com.example.navigationexample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationexample.data.entity.RentalDay


@Dao
interface RentalDaysDao {

    @Insert
    fun insertDay(day: RentalDay)

    @Query("DELETE FROM rentaldays WHERE client_id = :clientId")
    fun deleteClientDays(clientId: Long)


    @Query("SELECT * FROM rentaldays WHERE appatment_name=:appatmentName")
    fun getAppatmentDays(appatmentName: String): List<RentalDay>


    @Query("SELECT * FROM rentaldays")
    fun getAllRentalDays(): List<RentalDay>


//    @Query("SELECT * FROM rentaldays WHERE client_phone = :clientPhone")
//    fun getClientDays(clientPhone: String): List<RentalDay>
}