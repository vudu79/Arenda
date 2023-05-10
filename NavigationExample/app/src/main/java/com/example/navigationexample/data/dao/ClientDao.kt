package com.example.navigationexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.navigationexample.data.entity.Client


@Dao
interface ClientDao {
    @Insert
    fun insertClient(client: Client)

    @Query("DELETE FROM clients WHERE first_name = :name")
    fun deleteClient(name: String)

    @Query("SELECT * FROM clients WHERE first_name = :clientName")
    fun getClientByName(clientName: String): Client

    @Query("SELECT * FROM clients WHERE phone = :clientPhone")
    fun getClientByPhone(clientPhone: String): Client

    @Query("SELECT * FROM clients")
    fun getAllClients(): LiveData<List<Client>>

    @Query("SELECT * FROM clients WHERE appatment_name = :appatmentName")
    fun getAppatmentClients(appatmentName: String): List<Client>

    @Update
    fun updateClient(client: Client): Int
}