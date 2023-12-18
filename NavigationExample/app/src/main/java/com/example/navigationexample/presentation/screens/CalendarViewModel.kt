package com.example.navigationexample.presentation.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Apartment
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.domain.models.ClientMonk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val daysRepository: DaysRepositoryImpl,
) : ViewModel() {

    private val _isLoadingForSetPeriodScreen: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingForSetPeriodScreen: State<Boolean> get() = _isLoadingForSetPeriodScreen

    private val _isLoadingForCalendarScreen: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingForCalendarScreen: State<Boolean> get() = _isLoadingForCalendarScreen

    var allApartmentPlanedDays: Flow<List<LocalDate>> = flowOf()
    var localDayClientMocKMap: Flow<MutableMap<LocalDate, MutableSet<ClientMonk>>> = flowOf()

    var currentApartment = MutableLiveData<Apartment>()


    fun updateApartmentPlanedDays(apartmentName: String) {
        allApartmentPlanedDays = daysRepository.fetchAppatmentRentalDays(
            appatmentName = apartmentName,
            onStart = { _isLoadingForSetPeriodScreen.value = true },
            onCompletion = { _isLoadingForSetPeriodScreen.value = false },
            onError = { },
        )
    }

    fun updateDaysMapForCalendar(appatmentName: String) {
        localDayClientMocKMap = daysRepository.fetchRentalDayClientMocKMap(
            onStart = { _isLoadingForSetPeriodScreen.value = true },
            onCompletion = { _isLoadingForSetPeriodScreen.value = false },
            onError = { },
            apartmentName = appatmentName
        )
    }
}



