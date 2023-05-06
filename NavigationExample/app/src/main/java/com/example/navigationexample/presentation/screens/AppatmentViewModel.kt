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
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import javax.inject.Inject
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.navigationexample.domain.usecase.validation.*
import com.example.navigationexample.domain.usecase.validation.validators.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


@HiltViewModel
class AppatmentViewModel @Inject constructor(
    private val apartmentRepository: AppatmentRepositoryImpl,
    private val clientRepository: ClientsRepositoryImpl,
    private val daysRepository: DaysRepositoryImpl,
    private val getDayClientMapUseCase: GetDayClientMapUseCase,
    private val getApartmentPlanedDaysUseCase: getAppatmentPlanedDaysUseCase,

    private val nameValidationField: NameValidation = NameValidation(),
    private val phoneValidationField: PhoneValidation = PhoneValidation(),
    private val documentNumberValidationField: DocumentNumber = DocumentNumber(),
    private val documentDitailsValidationField: DocumentDitails = DocumentDitails(),
    private val membersValidationField: MembersValidation = MembersValidation(),
    private val paymentValidationField: PaymentValidation = PaymentValidation(),
    private val dateStringValidationField: DateStringValidation = DateStringValidation(),
    private val dateLongValidationField: DateLongValidation = DateLongValidation()
) : ViewModel() {

    private val _isLoadingForSetPeriodScreen: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingForSetPeriodScreen: State<Boolean> get() = _isLoadingForSetPeriodScreen

    private val _isLoadingForCalendarScreen: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingForCalendarScreen: State<Boolean> get() = _isLoadingForCalendarScreen

//    val allApartmentPlanedDays: Flow<Map<String, List<LocalDate>>> =
//        daysRepository.fetchAppatmentRentalDaysMap(
//            onStart = { _isLoadingForSetPeriodScreen.value = true },
//            onCompletion = { _isLoadingForSetPeriodScreen.value = false },
//            onError = { },
//        )

    var allApartmentPlanedDays: Flow<List<LocalDate>> = flowOf()
    var localDayClientMocKMap: Flow<MutableMap<LocalDate, MutableSet<ClientMonk>>> = flowOf()


    var currentApartment = MutableLiveData<Appatment>()
    val allApartments: LiveData<List<Appatment>>
    val allClients: LiveData<List<Client>>
    var allApartmentClients: MutableLiveData<List<Client>>
    var dateClientMapForObserve = MutableLiveData<MutableMap<LocalDate, MutableSet<ClientMonk>>>()

    var dateOutString by mutableStateOf("")
    var dateOutLong by mutableStateOf(0L)
    var dateInString by mutableStateOf("")
    var dateInLong by mutableStateOf(0L)

    val clientFirstName = MutableLiveData<String>("")
    val clientSecondName = MutableLiveData<String>("")
    val clientLastName = MutableLiveData<String>("")
    val phone = MutableLiveData<String>("")
    val documentNumber = MutableLiveData<String>("")
    val documentDitails = MutableLiveData<String>("")
    val members = MutableLiveData<String>("")
    val payment = MutableLiveData<String>("")
    val prepayment = MutableLiveData<String>("")
    val colorClient = MutableLiveData<Int>(Color(0xFFEF9A9A).toArgb())
    val sity = MutableLiveData<String>("")

    var dateOutString1 = MutableLiveData<String>("")
    var dateOutLong1 = MutableLiveData<Long>()
    var dateInString1 = MutableLiveData<String>("")
    var dateInLong1 = MutableLiveData<Long>()

    var validateFormState by mutableStateOf(ValidationFormState())
    private val validationEventChannel = Channel<ValidatAllFieldsResultEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    init {
//        val appatmentDb = AppatmentRoomDatabase.getInstance(application)
//        val appatmentDao = appatmentDb.appatmentDao()
//        val clientDao = appatmentDb.ClientDao()
//        appatmentRepository = AppatmentRepositoryImpl(appatmentDao = appatmentDao)
//        clientRepository = ClientsRepositoryImpl(clientDao = clientDao)

        allApartments = apartmentRepository.allAppatment
        allClients = clientRepository.allClients
        allApartmentClients = clientRepository.allAppatmentClients
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


    fun onFormEvent(event: ValidationFormEvent) {
        when (event) {
            is ValidationFormEvent.FirstNameChanged -> {
                validateFormState = validateFormState.copy(firstName = event.firstName)
            }
            is ValidationFormEvent.SecondNameChanged -> {
                validateFormState = validateFormState.copy(secondName = event.secondName)
            }
            is ValidationFormEvent.LastNameChanged -> {
                validateFormState = validateFormState.copy(lastName = event.lastName)
            }
            is ValidationFormEvent.PhoneChanged -> {
                validateFormState = validateFormState.copy(phone = event.phone)
            }
            is ValidationFormEvent.DocumentNamberChanged -> {
                validateFormState = validateFormState.copy(documentNamber = event.documentNamber)
            }
            is ValidationFormEvent.DocumentDitailsChanged -> {
                validateFormState = validateFormState.copy(documentDitails = event.documentDitails)
            }
            is ValidationFormEvent.MembersChanged -> {
                validateFormState = validateFormState.copy(members = event.members)
            }
            is ValidationFormEvent.InStringDateChanged -> {
                validateFormState = validateFormState.copy(dateInString = event.inDateString)
            }
            is ValidationFormEvent.InLongDateChanged -> {
                validateFormState = validateFormState.copy(dateInLong = event.inDateLong)
            }
            is ValidationFormEvent.OutStringDateChanged -> {
                validateFormState = validateFormState.copy(dateOutString = event.outDateString)
            }
            is ValidationFormEvent.OutLongDateChanged -> {
                validateFormState = validateFormState.copy(dateOutLong = event.outDateLong)
            }
            is ValidationFormEvent.PrepaymentChanged -> {
                validateFormState = validateFormState.copy(prePayment = event.prepayment)
            }
            is ValidationFormEvent.PaymentChanged -> {
                validateFormState = validateFormState.copy(payment = event.payment)
            }
            is ValidationFormEvent.SityChanged -> {
                validateFormState = validateFormState.copy(sity = event.sity)
            }
            is ValidationFormEvent.ColorChanged -> {
                validateFormState = validateFormState.copy(color = event.color)
            }

            is ValidationFormEvent.onSubmit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val firstNameResult = nameValidationField.execute(validateFormState.firstName, true)

        val dateInStringResult = dateStringValidationField.execute(validateFormState.dateInString)
        val dateOutStringResult = dateStringValidationField.execute(validateFormState.dateOutString)
        val dateInLongResult = dateLongValidationField.execute(validateFormState.dateInLong)
        val dateOutLongResult = dateLongValidationField.execute(validateFormState.dateOutLong)

        val secondNameResult = validateFormState.secondName?.let { nameValidationField.execute(it, false) }
        val lastNameResult =validateFormState.lastName?.let { nameValidationField.execute(it, false) }
        val phoneResult = phoneValidationField.execute(validateFormState.phone)
        val documentNumberResult = validateFormState.documentNamber?.let {
            documentNumberValidationField.execute(
                it
            )
        }
        val documentDitailsResult = validateFormState.documentDitails?.let {
            documentDitailsValidationField.execute(
                it, false
            )
        }



        val membersResult = validateFormState.members?.let {
            membersValidationField.execute(
                it
            )
        }
        val prePaymentResult = paymentValidationField.execute(validateFormState.prePayment)
        val paymentResult = paymentValidationField.execute(validateFormState.payment)

        val hasError = listOf(
            firstNameResult,
            secondNameResult,
            lastNameResult,
            phoneResult,
            documentNumberResult,
            documentDitailsResult,
            dateInLongResult,
            dateInStringResult,
            dateOutLongResult,
            dateOutStringResult,
            membersResult,
            prePaymentResult,
            paymentResult
        ).any { !it!!.successful }

        if (hasError) {
            validateFormState = validateFormState.copy(
             firstNameError=firstNameResult.errorMessage,
             secondNameError=secondNameResult?.errorMessage,
             lastNameError=lastNameResult?.errorMessage,
             phoneError=phoneResult.errorMessage,
             documentNamberError=documentNumberResult?.errorMessage,
             documentDitailsError=documentDitailsResult?.errorMessage,
             membersError=membersResult?.errorMessage,
             dateOutStringError=dateOutStringResult.errorMessage,
             dateInStringError=dateInStringResult.errorMessage,
             dateOutLongError=dateOutLongResult.errorMessage,
             dateInLongError=dateInLongResult.errorMessage,
             prePaymentError=prePaymentResult.errorMessage,
             paymentError=paymentResult.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidatAllFieldsResultEvent.Success)
        }
    }
}



