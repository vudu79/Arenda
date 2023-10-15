package com.example.navigationexample.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.navigationexample.data.entity.Client


@Dao
interface ClientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClient(client: Client): Long

    @Query("DELETE FROM clients WHERE client_id = :id")
    fun deleteClient(id: Long)

    @Query("SELECT * FROM clients WHERE first_name = :clientName")
    fun getClientByName(clientName: String): Client

    @Query("SELECT * FROM clients WHERE phone = :clientPhone")
    suspend fun getClientByPhone(clientPhone: String): Client

    @Query("SELECT * FROM clients WHERE client_id = :clientId")
    fun getClientById(clientId: Long): Client

    @Query("SELECT * FROM clients")
    fun getAllClients(): LiveData<List<Client>>

    @Query("SELECT * FROM clients WHERE appatment_name = :appatmentName")
    fun getAppatmentClients(appatmentName: String): List<Client>

    @Update
    suspend fun updateClient(client: Client): Int
}