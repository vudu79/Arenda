package com.example.navigationexample.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationexample.data.entity.Apartment


@Dao
interface ApartmentDao {
    @Query("SELECT * FROM apartments WHERE name = :name")
    fun getApartmentByName(name: String): Apartment

    @Insert
    fun insertApartment(apartment: Apartment)

    @Query("DELETE FROM apartments WHERE name = :name")
    fun deleteApartment(name: String)

    @Query("SELECT * FROM apartments")
    fun getAllApartment(): List<Apartment>

}