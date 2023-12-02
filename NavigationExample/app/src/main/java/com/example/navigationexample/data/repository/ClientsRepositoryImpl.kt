package com.example.navigationexample.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.entity.Client
import kotlinx.coroutines.*
import javax.inject.Inject


class ClientsRepositoryImpl @Inject constructor(private val clientDao: ClientDao) {

    val allApartmentClients = MutableLiveData<List<Client>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertClient(newClient: Client):  Deferred<Long> =
        coroutineScope.async (Dispatchers.IO) {
            return@async clientDao.insertClient(newClient)
        }


    suspend fun getClientByPhone(phone: String): Client {
        return clientDao.getClientByPhone(phone)
    }

    fun getClientById(clientId: Long): Client {
        return clientDao.getClientById(clientId)
    }

    suspend fun updateClient(client: Client): Int {
        return clientDao.updateClient(client)
    }

    fun deleteClient(id: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.deleteClient(id)
        }
    }

    fun getApartmentClients(apartmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allApartmentClients.value = asyncFindAllApartmentClients(apartmentName).await()
        }
    }

    private fun asyncFindAllApartmentClients(apartmentName: String): Deferred<List<Client>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async clientDao.getApartmentClients(apartmentName)
        }
}