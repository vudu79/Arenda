package com.example.navigationexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationexample.data.entity.Appatment


@Dao
interface AppatmentDao {

    @Insert
    fun insertAppatment(appatment: Appatment)

    @Query("DELETE FROM appatments WHERE name = :name")
    fun deleteAppatment(name: String)

    @Query("SELECT * FROM appatments")
    fun getAllAppatment(): LiveData<List<Appatment>>

}