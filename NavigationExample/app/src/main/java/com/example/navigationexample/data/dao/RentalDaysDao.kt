package com.example.navigationexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationexample.data.entity.RentalDay


@Dao
interface RentalDaysDao {

    @Insert
    fun insertDay(day: RentalDay)

    @Query("DELETE FROM rentaldays WHERE client_name = :clientName")
    fun deleteClientDays(clientName: String)


    @Query("SELECT * FROM rentaldays WHERE appatment_name=:appatmentName")
    fun getAllAppatmentDays(appatmentName: String): List<RentalDay>


    @Query("SELECT * FROM rentaldays WHERE client_name = :clientName")
    fun getClientDays(clientName: String): List<RentalDay>
}