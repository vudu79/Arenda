package com.example.navigationexample.presentation.screens

import android.app.Application
import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.AppatmentRoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.repository.AppatmentRepositoryImpl
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import java.text.SimpleDateFormat


class AppatmentViewModel(
    application: Application
) : ViewModel() {

    private val appatmentRepository: AppatmentRepositoryImpl
    private val clientRepository: ClientsRepositoryImpl
    val allAppatments: LiveData<List<Appatment>>
    val allClients: LiveData<List<Client>>
    var allAppatmentClients: MutableLiveData<List<Client>>
    var currentAppatment = mutableStateOf("")
    var dateOutString by mutableStateOf("")
    var dateOutLong by mutableStateOf(0L)
    var dateInString by mutableStateOf("")
    var dateInLong by mutableStateOf(0L)


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


    private var dateFormat = "yyyy-MM-dd"
    fun showDatePickerDialog(context: Context, dateType: String) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context, { _, year, month, day ->
                when(dateType){
                    "in" -> {
                        dateInString = getPickedDateAsString(year, month, day)
                        dateInLong = getPickedDateAsLong(year, month, day)
                    }
                    "out" -> {
                        dateOutString = getPickedDateAsString(year, month, day)
                        dateOutLong = getPickedDateAsLong(year, month, day)
                    }
                }

            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
            .show()
    }

//    private fun getCalendar(): Calendar {
//        return if (dateString.isEmpty())
//            Calendar.getInstance()
//        else
//            getLastPickedDateCalendar()
//    }
//
//    private fun getLastPickedDateCalendar(): Calendar {
//        val dateFormat = SimpleDateFormat(dateFormat)
//        val calendar = Calendar.getInstance()
//        calendar.time = dateFormat.parse(dateString)
//        return calendar
//    }


    private fun getPickedDateAsString(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat(dateFormat)
        return dateFormat.format(calendar.time)
    }

    private fun getPickedDateAsLong(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }



}