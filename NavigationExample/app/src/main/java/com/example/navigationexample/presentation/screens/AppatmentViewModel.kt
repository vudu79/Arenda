package com.example.navigationexample.presentation.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.repository.AppatmentRepositoryImpl
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.domain.models.ClientMonk
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class AppatmentViewModel @Inject constructor(
    private val apartmentRepository: AppatmentRepositoryImpl,
    private val clientRepository: ClientsRepositoryImpl,
    private val daysRepository: DaysRepositoryImpl,
//    private val getDayClientMapUseCase: GetDayClientMapUseCase,
//    private val getApartmentPlanedDaysUseCase: getAppatmentPlanedDaysUseCase,
//
//    private val nameValidationField: NameValidation = NameValidation(),
//    private val phoneValidationField: PhoneValidation = PhoneValidation(),
//    private val documentNumberValidationField: DocumentNumber = DocumentNumber(),
//    private val documentDitailsValidationField: DocumentDitails = DocumentDitails(),
//    private val membersValidationField: MembersValidation = MembersValidation(),
//    private val paymentValidationField: PaymentValidation = PaymentValidation(),
//    private val dateStringValidationField: DateStringValidation = DateStringValidation(),
//    private val dateLongValidationField: DateLongValidation = DateLongValidation()
) : ViewModel() {

    var currentApartment = MutableLiveData<Appatment>()
    val allApartments: LiveData<List<Appatment>>


    init {
        allApartments = apartmentRepository.allAppatment
    }

    fun setCurrentAppatment(appatment: Appatment) {
        currentApartment.value = appatment
        Log.d("myTag", "щелчок по объекту   -   ${currentApartment.value!!.name}")
    }

    fun insertAppatment(appatment: Appatment) {
        apartmentRepository.insertAppatment(appatment)
    }

    fun deleteAppatment(name: String) {
        apartmentRepository.deleteAppatment(name)
    }
}



