package com.example.navigationexample.presentation.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.repository.AppatmentRepositoryImpl
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.domain.models.ClientMonk
import com.example.navigationexample.domain.usecase.GetDayClientMapUseCase
import com.example.navigationexample.domain.usecase.getAppatmentPlanedDaysUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class AppatmentViewModel @Inject constructor(
    private val appatmentRepository: AppatmentRepositoryImpl,
    private val clientRepository: ClientsRepositoryImpl,
    private val daysRepository: DaysRepositoryImpl,
    private val getDayClientMapUseCase: GetDayClientMapUseCase,
    private val getAppatmentPlanedDaysUseCase: getAppatmentPlanedDaysUseCase

) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    val allAppatmentPlanedDays: Flow<Map<String, List<LocalDate>>> = daysRepository.fetchAppatmentRentalDaysMap(
        onStart = { _isLoading.value = true },
        onCompletion = { _isLoading.value = false },
        onError = { },
    )

    var currentAppatment = MutableLiveData<Appatment>()
    val allAppatments: LiveData<List<Appatment>>
    val allClients: LiveData<List<Client>>
    var allAppatmentClients: MutableLiveData<List<Client>>
    var dateClientMapForObserve = MutableLiveData<MutableMap<LocalDate, MutableSet<ClientMonk>>>()

    var dateOutString by mutableStateOf("")
    var dateOutLong by mutableStateOf(0L)
    var dateInString by mutableStateOf("")
    var dateInLong by mutableStateOf(0L)

    val clientName = MutableLiveData<String>("qwe")
    val phone = MutableLiveData<String>("")
    val members = MutableLiveData<String>("")
    val payment = MutableLiveData<String>("")
    val prepayment = MutableLiveData<String>("")
    val colorClient = MutableLiveData<Int>(Color(0xFFEF9A9A).toArgb())
    val sity = MutableLiveData<String>("")

    var dateOutString1 = MutableLiveData<String>("__.__.____")
    var dateOutLong1 = MutableLiveData<Long>()
    var dateInString1 = MutableLiveData<String>("__.__.____")
    var dateInLong1 = MutableLiveData<Long>()


    init {
//        val appatmentDb = AppatmentRoomDatabase.getInstance(application)
//        val appatmentDao = appatmentDb.appatmentDao()
//        val clientDao = appatmentDb.ClientDao()
//        appatmentRepository = AppatmentRepositoryImpl(appatmentDao = appatmentDao)
//        clientRepository = ClientsRepositoryImpl(clientDao = clientDao)

        allAppatments = appatmentRepository.allAppatment
        allClients = clientRepository.allClients
        allAppatmentClients = clientRepository.allAppatmentClients
    }

    fun setCurrentAppatment(appatment: Appatment) {
        currentAppatment.value = appatment
        Log.d("myTag", "щелчок по объекту   -   ${currentAppatment.value!!.name}")
    }

    fun insertAppatment(appatment: Appatment) {
        appatmentRepository.insertAppatment(appatment)
    }

    fun deleteAppatment(name: String) {
        appatmentRepository.deleteAppatment(name)
    }

    fun addClient(client: Client) {
        clientRepository.insertClient(client)
        daysRepository.insertClientDays(client)
    }

    fun deleteClient(name: String) {
        daysRepository.deleteClientDays(name)
        clientRepository.deleteClient(name)
    }

    fun getAppatmentClients(appatmentName: String) {
        clientRepository.getAppatmentClients(appatmentName)
    }

    fun updateDaysMapForCalendar(appatmentName: String) {

        dateClientMapForObserve.value?.clear()
        dateClientMapForObserve.value = getDayClientMapUseCase.invoke(appatmentName)
    }
}

//private var dateFormat = "yyyy-MM-dd"
//fun showDatePickerDialog(context: Context, dateType: String) {
//    val calendar = Calendar.getInstance()
//    DatePickerDialog(
//        context, { _, year, month, day ->
//            when (dateType) {
//                "in" -> {
//                    dateInString = getPickedDateAsString(year, month, day)
//                    dateInLong = getPickedDateAsLocalDate(year, month, day)
//                }
//                "out" -> {
//                    dateOutString = getPickedDateAsString(year, month, day)
//                    dateOutLong = getPickedDateAsLocalDate(year, month, day)
//                }
//            }
//
//        },
//        calendar.get(Calendar.YEAR),
//        calendar.get(Calendar.MONTH),
//        calendar.get(Calendar.DAY_OF_MONTH)
//    )
//        .show()
//}

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

//
//@SuppressLint("SimpleDateFormat")
//private fun getPickedDateAsString(year: Int, month: Int, day: Int): String {
//    val calendar = Calendar.getInstance()
//    calendar.set(year, month, day)
//    val dateFormat = SimpleDateFormat(dateFormat)
//    return dateFormat.format(calendar.time)
//}
//
//private fun getPickedDateAsLong(year: Int, month: Int, day: Int): Long {
//    val calendar = Calendar.getInstance()
//    calendar.set(year, month, day)
//    return calendar.timeInMillis
//}

//private fun getPickedDateAsLocalDate(year: Int, month: Int, day: Int): Long {
//    return LocalDate.of(year, month + 1, day).toEpochDay()
//}

//}