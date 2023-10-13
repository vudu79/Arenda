package com.example.navigationexample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.entity.Client
import kotlinx.coroutines.*
import java.time.LocalDate
import javax.inject.Inject


class ClientsRepositoryImpl @Inject constructor(private val clientDao: ClientDao) {

    var dateClientMap: MutableMap<LocalDate, MutableSet<Client>> = mutableMapOf()
    val allClients: LiveData<List<Client>> = clientDao.getAllClients()
    val allAppatmentClients = MutableLiveData<List<Client>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertClient(newClient: Client) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.insertClient(newClient)
        }
    }

    fun getClientByName(name: String): Client {
        return clientDao.getClientByName(name)
    }

    suspend fun getClientByPhone(phone: String): Client {
        // Log.d("tag", "Клиент взят из базы")
        return clientDao.getClientByPhone(phone)

    }

//    fun getClientByPhone(
//        onStart: () -> Unit,
//        onCompletion: () -> Unit,
//        onError: () -> Unit,
//        phone: String
//    ) = flow {
//        var validateFormState = ValidationFormState()
//        val client = clientDao.getClientByPhone(phone)
//        if (client == null) {
//            onError()
//        } else {
//            validateFormState = validateFormState.copy(status = client.status)
//            validateFormState = validateFormState.copy(firstName = client.firstName)
//            validateFormState = validateFormState.copy(secondName = client.secondName)
//            validateFormState = validateFormState.copy(lastName = client.lastName)
//            validateFormState = validateFormState.copy(phone = client.phone)
//            validateFormState = validateFormState.copy(documentNamber = client.documentNumber)
//            validateFormState = validateFormState.copy(documentDitails = client.documentDitails)
//            validateFormState = validateFormState.copy(members = client.members.toString())
//            validateFormState =
//                validateFormState.copy(dateInString = LocalDate.ofEpochDay(client.inDate).toString())
//            validateFormState = validateFormState.copy(dateInLong = client.inDate)
//            validateFormState =
//                validateFormState.copy(dateOutString = LocalDate.ofEpochDay(client.outDate).toString())
//            validateFormState = validateFormState.copy(dateOutLong = client.outDate)
//            validateFormState = validateFormState.copy(prePayment = client.prepayment.toString())
//            validateFormState = validateFormState.copy(transferInfo = client.transferInfo)
//            validateFormState = validateFormState.copy(referer = client.referer)
//            validateFormState = validateFormState.copy(color = Color(client.clientColor))
//
//            emit(validateFormState)
//        }
//    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)


    suspend fun updateClient(client: Client): Int {
        return clientDao.updateClient(client)
    }

    fun deleteClient(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.deleteClient(name)
        }
    }


    fun getAppatmentClients(appatmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allAppatmentClients.value = asyncFindAllApartmentClients(appatmentName).await()
        }
    }


    private fun asyncFindAllApartmentClients(appatmentName: String): Deferred<List<Client>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async clientDao.getAppatmentClients(appatmentName)
        }

}