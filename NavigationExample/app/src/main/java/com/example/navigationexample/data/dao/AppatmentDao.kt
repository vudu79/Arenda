package com.example.navigationexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationexample.data.entity.Appatment


@Dao
interface AppatmentDao {
    @Query("SELECT * FROM appatments WHERE name = :name")
    fun getApartmentByName(name: String): Appatment

    @Insert
    fun insertApartment(appatment: Appatment)

    @Query("DELETE FROM appatments WHERE name = :name")
    fun deleteApartment(name: String)

    @Query("SELECT * FROM appatments")
    fun getAllApartment(): List<Appatment>

}