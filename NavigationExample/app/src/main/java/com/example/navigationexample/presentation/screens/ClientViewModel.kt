package com.example.navigationexample.presentation.screens


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationexample.constants.Constans
import com.example.navigationexample.constants.ScoreType
import com.example.navigationexample.constants.SourceEvent
import com.example.navigationexample.data.dao.ScoresDao
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.entity.Score
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.domain.models.ClientStatus
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.domain.usecase.validation.validators.CompletedPaymentValidation
import com.example.navigationexample.domain.usecase.validation.validators.CompletedPrePaymentValidation
import com.example.navigationexample.domain.usecase.validation.validators.DateLongValidation
import com.example.navigationexample.domain.usecase.validation.validators.DateStringValidation
import com.example.navigationexample.domain.usecase.validation.validators.DocumentDitails
import com.example.navigationexample.domain.usecase.validation.validators.DocumentNumber
import com.example.navigationexample.domain.usecase.validation.validators.MembersValidation
import com.example.navigationexample.domain.usecase.validation.validators.NameValidation
import com.example.navigationexample.domain.usecase.validation.validators.OverPaymentValidation
import com.example.navigationexample.domain.usecase.validation.validators.PhoneValidation
import com.example.navigationexample.domain.usecase.validation.validators.PledgeValidation
import com.example.navigationexample.domain.usecase.validation.validators.PrePaymentValidation
import com.example.navigationexample.domain.usecase.validation.validators.PricePerDayValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject


@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientsRepositoryImpl,
    private val daysRepository: DaysRepositoryImpl,
    private val scoresDao: ScoresDao,

    private val nameValidationField: NameValidation = NameValidation(),
    private val phoneValidationField: PhoneValidation = PhoneValidation(),
    private val documentNumberValidationField: DocumentNumber = DocumentNumber(),
    private val documentDitailsValidationField: DocumentDitails = DocumentDitails(),
    private val membersValidationField: MembersValidation = MembersValidation(),
    private val pricePerDayValidationField: PricePerDayValidation = PricePerDayValidation(),
    private val prePaymentValidationField: PrePaymentValidation = PrePaymentValidation(),
    private val pledgeValidationField: PledgeValidation = PledgeValidation(),
    private val completedPrePaymentValidationField: CompletedPrePaymentValidation = CompletedPrePaymentValidation(),
    private val completedPaymentValidationField: CompletedPaymentValidation = CompletedPaymentValidation(),
    private val overPaymentValidation: OverPaymentValidation = OverPaymentValidation(),
    private val dateStringValidationField: DateStringValidation = DateStringValidation(),
    private val dateLongValidationField: DateLongValidation = DateLongValidation()
) : ViewModel() {
    var allApartmentClients: MutableLiveData<List<Client>>
    var validateFormState by mutableStateOf(ValidationFormState())

    private var _uiClientState = MutableLiveData<Client>()
    val uiClientState: LiveData<Client> = _uiClientState
    private var _paymentDebt = MutableLiveData<Int>()
    val paymentDebt: LiveData<Int> = _paymentDebt

    private var _isPaymentComplete = MutableLiveData<Boolean>(false)
    val isPaymentComplete: LiveData<Boolean> = _isPaymentComplete

    private var _isPrePaymentComplete = MutableLiveData<Boolean>(false)
    val isPrePaymentComplete: LiveData<Boolean> = _isPrePaymentComplete

    private var _isPledgeComplete = MutableLiveData<Boolean>(false)
    val isPledgeComplete: LiveData<Boolean> = _isPledgeComplete


    private val _isLoadingForUpdateClient: MutableState<Boolean> = mutableStateOf(false)
    val isLoadingForUpdateClient: State<Boolean> get() = _isLoadingForUpdateClient

    init {
        allApartmentClients = clientRepository.allApartmentClients
    }

    private val validationEventChannel = Channel<ValidatAllFieldsResultEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    var dateOutString by mutableStateOf("")
    var dateOutLong by mutableStateOf(0L)
    var dateInString by mutableStateOf("")
    var dateInLong by mutableStateOf(0L)

    fun getClientStateForValidation(phone: String) = viewModelScope.launch {
        val client = clientRepository.getClientByPhone(phone)
        validateFormState = validateFormState.copy(apartmentName = client.appatmentName)
        validateFormState = validateFormState.copy(id = client.id)
        validateFormState = validateFormState.copy(status = client.status)
        validateFormState = validateFormState.copy(firstName = client.firstName)
        validateFormState = validateFormState.copy(secondName = client.secondName)
        validateFormState = validateFormState.copy(lastName = client.lastName)
        validateFormState = validateFormState.copy(phone = client.phone)
        validateFormState = validateFormState.copy(documentNamber = client.documentNumber)
        validateFormState = validateFormState.copy(documentDitails = client.documentDitails)
        validateFormState = validateFormState.copy(members = client.members.toString())
        validateFormState = validateFormState.copy(overMembers = client.overMembers.toString())
        validateFormState =
            validateFormState.copy(dateInString = LocalDate.ofEpochDay(client.inDate).toString())
        validateFormState = validateFormState.copy(dateInLong = client.inDate)
        validateFormState =
            validateFormState.copy(dateOutString = LocalDate.ofEpochDay(client.outDate).toString())
        validateFormState = validateFormState.copy(dateOutLong = client.outDate)
        validateFormState = validateFormState.copy(priceOfStay = client.priceOfStay.toString())
        validateFormState = validateFormState.copy(pricePerDay = client.pricePerDay.toString())
        validateFormState = validateFormState.copy(overPayment = client.overPayment.toString())
        validateFormState =
            validateFormState.copy(prePaymentPercent = client.prePaymentPercent.toString())
        validateFormState =
            validateFormState.copy(prePayment = client.prePayment.toString())
        validateFormState = validateFormState.copy(pledge = client.pledge.toString())


        validateFormState =
            validateFormState.copy(completedPayment = client.completedPayment.toString())
        validateFormState =
            validateFormState.copy(dateOfCompletedPayment = client.dateOfCompletedPayment)

        validateFormState =
            validateFormState.copy(completedPrePayment = client.completedPrePayment.toString())
        validateFormState =
            validateFormState.copy(dateOfCompletedPrePayment = client.dateOfCompletedPrePayment)

        validateFormState =
            validateFormState.copy(completedPledge = client.completedPledge.toString())
        validateFormState =
            validateFormState.copy(dateOfCompletedPledge = client.dateOfCompletedPledge)

        validateFormState =
            validateFormState.copy(completedOverPayment = client.completedOverPayment.toString())
        validateFormState = validateFormState.copy(transferInfo = client.transferInfo)
        validateFormState = validateFormState.copy(referer = client.referer)
        validateFormState = validateFormState.copy(color = Color(client.clientColor))
        Log.d("myTag", "стейт клиента обнавлен")
    }

    fun resetClientStateForValidation() = viewModelScope.launch {
        validateFormState = validateFormState.copy(apartmentName = "")
        validateFormState = validateFormState.copy(id = 0)
        validateFormState = validateFormState.copy(status = ClientStatus.intrasting)
        validateFormState = validateFormState.copy(firstName = "")
        validateFormState = validateFormState.copy(secondName = "")
        validateFormState = validateFormState.copy(lastName = "")
        validateFormState = validateFormState.copy(phone = "")
        validateFormState = validateFormState.copy(documentNamber = "")
        validateFormState = validateFormState.copy(documentDitails = "")
        validateFormState = validateFormState.copy(members = "")
        validateFormState = validateFormState.copy(overMembers = "")
        validateFormState =
            validateFormState.copy(dateInString = "")
        validateFormState = validateFormState.copy(dateInLong = 0L)
        validateFormState =
            validateFormState.copy(dateOutString = "")
        validateFormState = validateFormState.copy(dateOutLong = 0L)
        validateFormState = validateFormState.copy(pricePerDay = "")
        validateFormState = validateFormState.copy(overPayment = "")
        validateFormState = validateFormState.copy(prePaymentPercent = "")
        validateFormState = validateFormState.copy(prePayment = "")
        validateFormState = validateFormState.copy(priceOfStay = "")

        validateFormState = validateFormState.copy(completedPrePayment = "0")
        validateFormState = validateFormState.copy(completedPayment = "0")
        validateFormState = validateFormState.copy(completedPledge = "0")

        validateFormState = validateFormState.copy(dateOfCompletedPrePayment = 0)
        validateFormState = validateFormState.copy(dateOfCompletedPayment = 0)
        validateFormState = validateFormState.copy(dateOfCompletedPledge = 0)

        validateFormState = validateFormState.copy(transferInfo = "")
        validateFormState = validateFormState.copy(referer = "")
        validateFormState =
            validateFormState.copy(color = Constans.ClientColorsList.clientColorsList[0])
        validateFormState = validateFormState.copy(firstNameError = null)
        validateFormState = validateFormState.copy(secondNameError = null)
        validateFormState = validateFormState.copy(lastNameError = null)
        validateFormState = validateFormState.copy(phoneError = null)
        validateFormState = validateFormState.copy(documentNamberError = null)
        validateFormState = validateFormState.copy(documentDitailsError = null)
        validateFormState = validateFormState.copy(membersError = null)
        validateFormState = validateFormState.copy(dateOutStringError = null)
        validateFormState = validateFormState.copy(dateInStringError = null)
        validateFormState = validateFormState.copy(dateOutLongError = null)
        validateFormState = validateFormState.copy(dateInLongError = null)
        validateFormState = validateFormState.copy(prePaymentPercentError = null)
        validateFormState = validateFormState.copy(prePaymentError = null)
        validateFormState = validateFormState.copy(pricePerDayError = null)
        validateFormState = validateFormState.copy(transferInfoError = null)
        validateFormState = validateFormState.copy(refererError = null)
        validateFormState = validateFormState.copy(colorError = null)
        validateFormState = validateFormState.copy(completedPrePaymentError = null)
        validateFormState = validateFormState.copy(completedPaymentError = null)
        validateFormState = validateFormState.copy(completedPledgeError = null)

    }


    fun getClientState(clientPhone: String) {
        viewModelScope.launch {
            _uiClientState.value = clientRepository.getClientByPhone(clientPhone)
            getPaymentDebt()
            _isPrePaymentComplete.value =
                _uiClientState.value!!.prePayment == _uiClientState.value!!.completedPrePayment
            _isPaymentComplete.value = _paymentDebt.value == _uiClientState.value!!.completedPayment
            _isPledgeComplete.value =
                _uiClientState.value!!.pledge == _uiClientState.value!!.completedPledge
        }
    }

    private fun getPaymentDebt() {
        _paymentDebt.value =
            (uiClientState.value?.priceOfStay?.minus(uiClientState.value?.completedPrePayment!!)
                ?: 0)
    }

    fun setActivePrePaymentTextField() {
        _isPrePaymentComplete.value = !_isPrePaymentComplete.value!!
    }

    fun setActivePaymentTextField() {
        _isPaymentComplete.value = !_isPaymentComplete.value!!
    }

    fun setActivePledgeTextField() {
        _isPledgeComplete.value = !_isPledgeComplete.value!!
    }

    fun getApartmentClients(apartmentName: String) {
        clientRepository.getApartmentClients(apartmentName)
    }


    suspend fun addClient(client: Client) {
        val clientId = clientRepository.insertClient(client).await()
        daysRepository.insertClientDays(client, clientId)
    }

    fun deleteClient(clientId: Long) {
        daysRepository.deleteClientDays(clientId)
        clientRepository.deleteClient(clientId)
    }

    private suspend fun updateClient(client: Client): Int {
        val result = clientRepository.updateClient(client = client)
        updateClientDays(client, result)
        return result
    }


    private suspend fun updateClientDays(client: Client, result: Int) {
        val ee = daysRepository.updateClientDays(
            result = result,
            client = client,
            onStart = {
                _isLoadingForUpdateClient.value = true
                Log.d("myTag", "старт")
            },
            onCompletion = {
                _isLoadingForUpdateClient.value = false
                Log.d("myTag", "финиш")
            },
            onError = { Log.d("myTag", "Ошибка обновления дней клиента") },
        ).collect()
    }


    fun onFormEvent(event: ValidationFormEvent) {
        when (event) {
            is ValidationFormEvent.StatusChanged -> {
                validateFormState = validateFormState.copy(status = event.status)
            }

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

            is ValidationFormEvent.OverMembersChanged -> {
                validateFormState = validateFormState.copy(overMembers = event.overMembers)
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

            is ValidationFormEvent.PrePaymentChanged -> {
                validateFormState = validateFormState.copy(prePayment = event.prepayment)
            }

            is ValidationFormEvent.PrePaymentPercentChanged -> {
                validateFormState =
                    validateFormState.copy(prePaymentPercent = event.prepaymentPercent)
            }

            is ValidationFormEvent.PricePerDayChanged -> {
                validateFormState = validateFormState.copy(pricePerDay = event.pricePerDay)
            }

            is ValidationFormEvent.PledgeChanged -> {
                validateFormState = validateFormState.copy(pledge = event.pledge)
            }

            is ValidationFormEvent.OverPaymentChanged -> {
                validateFormState = validateFormState.copy(overPayment = event.overPayment)
            }


            is ValidationFormEvent.CompletedPrePaymentChanged -> {
                validateFormState =
                    validateFormState.copy(completedPrePayment = event.completedPrepayment)

                validateFormState =
                    validateFormState.copy(
                        dateOfCompletedPrePayment = LocalDate.now().toEpochDay()
                    )
            }

            is ValidationFormEvent.CompletedPaymentChanged -> {
                validateFormState =
                    validateFormState.copy(completedPayment = event.completedPayment)

                validateFormState =
                    validateFormState.copy(
                        dateOfCompletedPayment = LocalDate.now().toEpochDay()
                    )
            }

            is ValidationFormEvent.CompletedPledgeChanged -> {
                validateFormState = validateFormState.copy(completedPledge = event.completedPledge)

                validateFormState =
                    validateFormState.copy(
                        dateOfCompletedPledge = LocalDate.now().toEpochDay()
                    )
            }


            is ValidationFormEvent.CompletedOverPaymentChanged -> {
                validateFormState =
                    validateFormState.copy(completedOverPayment = event.completedOverPayment)
            }


            is ValidationFormEvent.transferInfoChanged -> {
                validateFormState = validateFormState.copy(transferInfo = event.transferInfo)
            }

            is ValidationFormEvent.refererChanged -> {
                validateFormState = validateFormState.copy(referer = event.referer)
            }

            is ValidationFormEvent.ColorChanged -> {
                validateFormState = validateFormState.copy(color = event.color)
            }

            is ValidationFormEvent.onSubmitInsert -> {
                submitDataInsert(event.apartmentName)
            }

            is ValidationFormEvent.onSubmitUpdate -> {
                submitDataUpdate(event.source)
            }
        }
    }

    private fun submitDataInsert(apartmentName: String) {
        val firstNameResult = nameValidationField.execute(validateFormState.firstName, true)
        val dateInStringResult = dateStringValidationField.execute(validateFormState.dateInString)
        val dateOutStringResult = dateStringValidationField.execute(validateFormState.dateOutString)
        val dateInLongResult = dateLongValidationField.execute(validateFormState.dateInLong)
        val dateOutLongResult = dateLongValidationField.execute(validateFormState.dateOutLong)
        val secondNameResult =
            validateFormState.secondName?.let { nameValidationField.execute(it, false) }
        val lastNameResult =
            validateFormState.lastName?.let { nameValidationField.execute(it, false) }
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
        val membersResult = membersValidationField.execute(validateFormState.members)

        val overMembersResult = membersValidationField.execute(validateFormState.overMembers)

        val pricePerDayResult = pricePerDayValidationField.execute(
            validateFormState
        )

        val pledgeResult = pledgeValidationField.execute(
            validateFormState.pledge
        )

        val prePaymentResult = prePaymentValidationField.execute(
            validateFormState
        )


        val overPaymentResult = overPaymentValidation.execute(
            validateFormState.overPayment
        )
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
            overMembersResult,
            prePaymentResult,
            pricePerDayResult,
            overPaymentResult,
            pledgeResult
        ).any { !it!!.successful }

        if (hasError) {
            validateFormState = validateFormState.copy(
                firstNameError = firstNameResult.errorMessage,
                secondNameError = secondNameResult?.errorMessage,
                lastNameError = lastNameResult?.errorMessage,
                phoneError = phoneResult.errorMessage,
                documentNamberError = documentNumberResult?.errorMessage,
                documentDitailsError = documentDitailsResult?.errorMessage,
                membersError = membersResult.errorMessage,
                overMembersError = overMembersResult.errorMessage,
                dateOutStringError = dateOutStringResult.errorMessage,
                dateInStringError = dateInStringResult.errorMessage,
                dateOutLongError = dateOutLongResult.errorMessage,
                dateInLongError = dateInLongResult.errorMessage,
                prePaymentPercentError = prePaymentResult.errorMessage,
                pricePerDayError = pricePerDayResult.errorMessage,
                overPaymentError = overPaymentResult.errorMessage,
                pledgeError = pledgeResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            addClient(
                Client(
                    status = validateFormState.status,
                    firstName = validateFormState.firstName.trim(),
                    secondName = validateFormState.secondName?.trim(),
                    lastName = validateFormState.lastName?.trim(),
                    phone = validateFormState.phone.trim(),
                    documentNumber = validateFormState.documentNamber!!.trim(),
                    documentDitails = validateFormState.documentDitails!!.trim(),
                    inDate = validateFormState.dateInLong,
                    outDate = validateFormState.dateOutLong,
                    members = validateFormState.members.trim().toInt(),
                    overMembers = validateFormState.overMembers.trim().toInt(),
                    overPayment = validateFormState.overPayment.trim().toInt(),

                    priceOfStay = priceOfStayCalculation(validateFormState),
                    daysOfStay = daysCalculation(validateFormState),
                    prePaymentPercent = validateFormState.prePaymentPercent.trim().toInt(),
                    prePayment = prePaymentCalculation(validateFormState),
                    pricePerDay = validateFormState.pricePerDay.trim().toInt(),
                    pledge = validateFormState.pledge.trim().toInt(),

                    completedPayment = validateFormState.completedPayment.trim().toInt(),
                    completedPrePayment = validateFormState.completedPrePayment.trim().toInt(),
                    completedPledge = validateFormState.completedPledge.trim().toInt(),

                    dateOfCompletedPayment = validateFormState.dateOfCompletedPayment,
                    dateOfCompletedPrePayment = validateFormState.dateOfCompletedPrePayment,
                    dateOfCompletedPledge = validateFormState.dateOfCompletedPledge,

                    completedOverPayment = validateFormState.completedOverPayment.trim().toInt(),
                    clientColor = validateFormState.color.toArgb(),
                    transferInfo = validateFormState.transferInfo,
                    referer = validateFormState.referer,
                    appatmentName = apartmentName
                )
            )
            clientRepository.getApartmentClients(apartmentName)
            validationEventChannel.send(ValidatAllFieldsResultEvent.InsertSuccess)
        }
    }

    private fun submitDataUpdate(source: SourceEvent) {
        val firstNameResult = nameValidationField.execute(validateFormState.firstName, true)
        val dateInStringResult = dateStringValidationField.execute(validateFormState.dateInString)
        val dateOutStringResult = dateStringValidationField.execute(validateFormState.dateOutString)
        val dateInLongResult = dateLongValidationField.execute(validateFormState.dateInLong)
        val dateOutLongResult = dateLongValidationField.execute(validateFormState.dateOutLong)
        val secondNameResult =
            validateFormState.secondName?.let { nameValidationField.execute(it, false) }
        val lastNameResult =
            validateFormState.lastName?.let { nameValidationField.execute(it, false) }
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
        val membersResult = membersValidationField.execute(validateFormState.members)
        val overMembersResult = membersValidationField.execute(validateFormState.overMembers)

        val prePaymentResult = prePaymentValidationField.execute(
            validateFormState
        )
        val pricePerDayResult = pricePerDayValidationField.execute(
            validateFormState
        )
        val pledgeResult = pledgeValidationField.execute(
            validateFormState.pledge
        )

        val overPaymentResult = overPaymentValidation.execute(
            validateFormState.overPayment
        )

        val completedPrePaymentResult = completedPrePaymentValidationField.execute(
            validateFormState
        )
        val completedPaymentResult = completedPaymentValidationField.execute(
            validateFormState, paymentDebt.value!!
        )
        val completedPledgeResult = pledgeValidationField.execute(
            validateFormState.pledge
        )

        val hasErrorList = listOf(
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
            overMembersResult,
            prePaymentResult,
            overPaymentResult,
            pledgeResult,
            pricePerDayResult,
            completedPrePaymentResult,
            completedPaymentResult,
            completedPledgeResult
        )

        val hasError: Boolean = hasErrorList.any { !it!!.successful }

        if (hasError) {
            Log.d("myTag", "hasError true")
            validateFormState = validateFormState.copy(
                firstNameError = firstNameResult.errorMessage,
                secondNameError = secondNameResult?.errorMessage,
                lastNameError = lastNameResult?.errorMessage,
                phoneError = phoneResult.errorMessage,
                documentNamberError = documentNumberResult?.errorMessage,
                documentDitailsError = documentDitailsResult?.errorMessage,
                membersError = membersResult.errorMessage,
                overMembersError = overMembersResult.errorMessage,
                dateOutStringError = dateOutStringResult.errorMessage,
                dateInStringError = dateInStringResult.errorMessage,
                dateOutLongError = dateOutLongResult.errorMessage,
                dateInLongError = dateInLongResult.errorMessage,
                prePaymentPercentError = prePaymentResult.errorMessage,
                pricePerDayError = pricePerDayResult.errorMessage,
                pledgeError = pledgeResult.errorMessage,
                overPaymentError = overPaymentResult.errorMessage,
                completedPrePaymentError = completedPrePaymentResult.errorMessage,
                completedPaymentError = completedPaymentResult.errorMessage,
                completedPledgeError = completedPledgeResult.errorMessage
            )
            viewModelScope.launch {
                validationEventChannel.send(ValidatAllFieldsResultEvent.UpdateWrong(hasErrorList = hasErrorList))
            }
            return
        }
        viewModelScope.launch {
            if (validateFormState.id != 0L) {
                updateClient(
                    Client(
                        id = validateFormState.id!!,
                        status = validateFormState.status,
                        firstName = validateFormState.firstName.trim(),
                        secondName = validateFormState.secondName?.trim(),
                        lastName = validateFormState.lastName?.trim(),
                        phone = validateFormState.phone.trim(),
                        documentNumber = validateFormState.documentNamber!!.trim(),
                        documentDitails = validateFormState.documentDitails!!.trim(),
                        inDate = validateFormState.dateInLong,
                        outDate = validateFormState.dateOutLong,
                        members = validateFormState.members.trim().toInt(),
                        overMembers = validateFormState.overMembers.trim().toInt(),
                        overPayment = validateFormState.overPayment.trim().toInt(),
                        priceOfStay = priceOfStayCalculation(validateFormState),
                        daysOfStay = daysCalculation(validateFormState),
                        prePaymentPercent = validateFormState.prePaymentPercent.trim().toInt(),
                        prePayment = prePaymentCalculation(validateFormState),
                        pricePerDay = validateFormState.pricePerDay.trim().toInt(),
                        pledge = validateFormState.pledge.trim().toInt(),

                        completedPayment = if (validateFormState.completedPayment == "") 0 else validateFormState.completedPayment.trim()
                            .toInt(),
                        completedPrePayment = if (validateFormState.completedPrePayment == "") 0 else validateFormState.completedPrePayment.trim()
                            .toInt(),
                        completedPledge = if (validateFormState.completedPledge == "") 0 else validateFormState.completedPledge.trim()
                            .toInt(),
                        completedOverPayment = validateFormState.completedOverPayment.trim()
                            .toInt(),

                        dateOfCompletedPayment = validateFormState.dateOfCompletedPayment,
                        dateOfCompletedPrePayment = validateFormState.dateOfCompletedPrePayment,
                        dateOfCompletedPledge = validateFormState.dateOfCompletedPledge,

                        clientColor = validateFormState.color.toArgb(),
                        transferInfo = validateFormState.transferInfo,
                        referer = validateFormState.referer,
                        appatmentName = validateFormState.apartmentName!!
                    )
                )



                if (validateFormState.prePayment != "" && validateFormState.completedPrePayment != "" && validateFormState.prePayment.toInt() == validateFormState.completedPrePayment.toInt()) {
                    scoresDao.insertScore(
                        Score(
                            validateFormState.completedPrePayment.toInt(),
                            LocalDate.now().toEpochDay(),
                            validateFormState.id,
                            "clients",
                            ScoreType.INCOME,
                            validateFormState.apartmentName!!
                        )
                    )
                }

                if (validateFormState.priceOfStay != "" &&
                    validateFormState.prePayment != "" &&
                    validateFormState.completedPayment != "" &&
                    (validateFormState.priceOfStay.toInt() - validateFormState.prePayment.toInt()) == validateFormState.completedPayment.toInt()
                ) {
                    scoresDao.insertScore(
                        Score(
                            validateFormState.completedPayment.toInt(),
                            LocalDate.now().toEpochDay(),
                            validateFormState.id,
                            "clients",
                            ScoreType.INCOME,
                            validateFormState.apartmentName!!
                        )
                    )
                }


            }
            validationEventChannel.send(ValidatAllFieldsResultEvent.UpdateSuccess(source))
        }
    }
}

fun daysCalculation(state: ValidationFormState): Int {
    val inDate = state.dateInLong
    val outDate = state.dateOutLong
    val ldInDate = LocalDate.ofEpochDay(inDate)
    val ldOutDate = LocalDate.ofEpochDay(outDate)
    return ChronoUnit.DAYS.between(ldInDate, ldOutDate).toInt()

}

fun priceOfStayCalculation(state: ValidationFormState): Int {
    val totalDays = daysCalculation(state)
    val pricePerDay = if (state.pricePerDay == "") 0 else state.pricePerDay.toInt()
    val overPayment = if (state.overPayment == "") 0 else state.overPayment.toInt()
    val overMembers = if (state.overMembers == "") 0 else state.overMembers.toInt()
    val members = if (state.members == "") 0 else state.members.toInt()

    return if (members <= overMembers) {
        (pricePerDay * totalDays)
    } else {
        (pricePerDay * totalDays) + (members - overMembers) * totalDays * overPayment
    }
}

fun prePaymentCalculation(state: ValidationFormState): Int {
    val prePaymentPercent =
        if (state.prePaymentPercent == "") 0 else state.prePaymentPercent.toInt()
    val totalCoast = priceOfStayCalculation(state)
    return (totalCoast / 100) * prePaymentPercent
}

