package com.example.navigationexample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.entity.Client
import kotlinx.coroutines.*


class ClientsRepositoryImpl(private val clientDao: ClientDao) {

    val allClients: LiveData<List<Client>> = clientDao.getAllClients()
    val allAppatmentClients = MutableLiveData<List<Client>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertClient(newClient: Client) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.insertClient(newClient)
        }
    }

    fun deleteClient(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.deleteClient(name)
        }
    }


    fun getAppatmentClients(appatmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allAppatmentClients.value = asyncFind(appatmentName).await()
        }
    }

    private fun asyncFind(appatmentName: String): Deferred<List<Client>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async clientDao.getAppatmentClients(appatmentName)
        }

}