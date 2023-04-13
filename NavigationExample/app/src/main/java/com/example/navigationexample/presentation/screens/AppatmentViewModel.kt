package com.example.navigationexample.presentation.screens

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.AppatmentRoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.repository.AppatmentRepositoryImpl
import com.example.navigationexample.data.repository.ClientsRepositoryImpl


class AppatmentViewModel(
    application: Application
) : ViewModel() {

    private val appatmentRepository: AppatmentRepositoryImpl
    private val clientRepository: ClientsRepositoryImpl
    val allAppatments: LiveData<List<Appatment>>
    val allClients: LiveData<List<Client>>
    var allAppatmentClients: MutableLiveData<List<Client>>
    var currentAppatment = mutableStateOf("")


    init {
        val appatmentDb = AppatmentRoomDatabase.getInstance(application)
        val appatmentDao = appatmentDb.appatmentDao()
        val clientDao = appatmentDb.ClientDao()
        appatmentRepository = AppatmentRepositoryImpl(appatmentDao = appatmentDao)
        clientRepository = ClientsRepositoryImpl(clientDao = clientDao)

        allAppatments = appatmentRepository.allAppatment

        allClients = clientRepository.allClients
        allAppatmentClients = clientRepository.allAppatmentClients
    }

    fun insertAppatment(appatment: Appatment) {
        appatmentRepository.insertAppatment(appatment)
    }

    fun deleteAppatment(name: String) {
        appatmentRepository.deleteAppatment(name)
    }

    fun addClient(client: Client) {
        clientRepository.insertClient(client)
    }

    fun deleteClient(name: String) {
        clientRepository.deleteClient(name)
    }

    fun getAppatmentClients(appatmentName: String) {
        clientRepository.getAppatmentClients(appatmentName)
    }

}