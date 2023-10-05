package com.example.navigationexample.presentation.screens

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewModelScope
import com.example.navigationexample.constants.Constans
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.domain.models.ClientStatus
import com.example.navigationexample.domain.usecase.validation.*
import com.example.navigationexample.domain.usecase.validation.validators.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate


@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientsRepositoryImpl,
    private val daysRepository: DaysRepositoryImpl,

    private val nameValidationField: NameValidation = NameValidation(),
    private val phoneValidationField: PhoneValidation = PhoneValidation(),
    private val documentNumberValidationField: DocumentNumber = DocumentNumber(),
    private val documentDitailsValidationField: DocumentDitails = DocumentDitails(),
    private val membersValidationField: MembersValidation = MembersValidation(),
    private val paymentValidationField: PaymentValidation = PaymentValidation(),
    private val dateStringValidationField: DateStringValidation = DateStringValidation(),
    private val dateLongValidationField: DateLongValidation = DateLongValidation()
) : ViewModel() {
    var allApartmentClients: MutableLiveData<List<Client>>
    var currentApartment = MutableLiveData<Appatment>()
    var validateFormState by mutableStateOf(ValidationFormState())

    init {
        allApartmentClients = clientRepository.allAppatmentClients
    }

    private val validationEventChannel = Channel<ValidatAllFieldsResultEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    var dateOutString by mutableStateOf("")
    var dateOutLong by mutableStateOf(0L)
    var dateInString by mutableStateOf("")
    var dateInLong by mutableStateOf(0L)

    fun getClientState(phone: String) = viewModelScope.launch {
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
        validateFormState =
            validateFormState.copy(dateInString = LocalDate.ofEpochDay(client.inDate).toString())
        validateFormState = validateFormState.copy(dateInLong = client.inDate)
        validateFormState =
            validateFormState.copy(dateOutString = LocalDate.ofEpochDay(client.outDate).toString())
        validateFormState = validateFormState.copy(dateOutLong = client.outDate)
        validateFormState = validateFormState.copy(payment = client.payment.toString())
        validateFormState = validateFormState.copy(prePayment = client.prepayment.toString())
        validateFormState = validateFormState.copy(transferInfo = client.transferInfo)
        validateFormState = validateFormState.copy(referer = client.referer)
        validateFormState = validateFormState.copy(color = Color(client.clientColor))
    }

    fun resetState() = viewModelScope.launch {
        validateFormState = validateFormState.copy(apartmentName = "")
        validateFormState = validateFormState.copy(id = 0)
        validateFormState = validateFormState.copy(status = ClientStatus.waiting)
        validateFormState = validateFormState.copy(firstName = "")
        validateFormState = validateFormState.copy(secondName = "")
        validateFormState = validateFormState.copy(lastName = "")
        validateFormState = validateFormState.copy(phone = "")
        validateFormState = validateFormState.copy(documentNamber = "")
        validateFormState = validateFormState.copy(documentDitails = "")
        validateFormState = validateFormState.copy(members = "")
        validateFormState =
            validateFormState.copy(dateInString = "")
        validateFormState = validateFormState.copy(dateInLong = 0L)
        validateFormState =
            validateFormState.copy(dateOutString = "")
        validateFormState = validateFormState.copy(dateOutLong = 0L)
        validateFormState = validateFormState.copy(payment = "")
        validateFormState = validateFormState.copy(prePayment = "")
        validateFormState = validateFormState.copy(transferInfo = "")
        validateFormState = validateFormState.copy(referer = "")
        validateFormState =
            validateFormState.copy(color = Constans.ClientColorsList.clientColorsList[0])
    }

    fun getAppatmentClients(appatmentName: String) {
        clientRepository.getAppatmentClients(appatmentName)
    }


    fun addClient(client: Client) {
        clientRepository.insertClient(client)
        daysRepository.insertClientDays(client)
    }

    fun deleteClient(name: String) {
        daysRepository.deleteClientDays(name)
        clientRepository.deleteClient(name)
    }

    suspend fun updateClient(client: Client): Int {
        return clientRepository.updateClient(client = client)
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
//                // Log.d("myTag", "asasd --- ${event.members}")
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
//                // Log.d("myTag", "asasd --- ${event.prepayment}")
                validateFormState = validateFormState.copy(prePayment = event.prepayment)
            }
            is ValidationFormEvent.PaymentChanged -> {
//                // Log.d("myTag", "asasd --- ${event.payment}")
                validateFormState = validateFormState.copy(payment = event.payment)
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
                // Log.d("myTag", "аппат - ${event.apartmentName}")
                submitDataInsert(event.apartmentName)
            }

            is ValidationFormEvent.onSubmitUpdate -> {
                submitDataUpdate()
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
                firstNameError = firstNameResult.errorMessage,
                secondNameError = secondNameResult?.errorMessage,
                lastNameError = lastNameResult?.errorMessage,
                phoneError = phoneResult.errorMessage,
                documentNamberError = documentNumberResult?.errorMessage,
                documentDitailsError = documentDitailsResult?.errorMessage,
                membersError = membersResult.errorMessage,
                dateOutStringError = dateOutStringResult.errorMessage,
                dateInStringError = dateInStringResult.errorMessage,
                dateOutLongError = dateOutLongResult.errorMessage,
                dateInLongError = dateInLongResult.errorMessage,
                prePaymentError = prePaymentResult.errorMessage,
                paymentError = paymentResult.errorMessage,
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
                    prepayment = validateFormState.prePayment.trim().toInt(),
                    payment = validateFormState.payment.trim().toInt(),
                    clientColor = validateFormState.color.toArgb(),
                    transferInfo = validateFormState.transferInfo,
                    referer = validateFormState.referer,
                    appatmentName = apartmentName
                )
            )
            clientRepository.getAppatmentClients(apartmentName)
            validationEventChannel.send(ValidatAllFieldsResultEvent.InsertSuccess)
        }
    }

    private fun submitDataUpdate() {
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
        val prePaymentResult = paymentValidationField.execute(validateFormState.prePayment)
        val paymentResult = paymentValidationField.execute(validateFormState.payment)

        val hasErrorList = listOf(
            firstNameResult,
            secondNameResult,
            lastNameResult,
            phoneResult,
            documentNumberResult,
            documentDitailsResult,
//            dateInLongResult,
//            dateInStringResult,
//            dateOutLongResult,
//            dateOutStringResult,
            membersResult,
            prePaymentResult,
            paymentResult
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
                dateOutStringError = dateOutStringResult.errorMessage,
                dateInStringError = dateInStringResult.errorMessage,
                dateOutLongError = dateOutLongResult.errorMessage,
                dateInLongError = dateInLongResult.errorMessage,
                prePaymentError = prePaymentResult.errorMessage,
                paymentError = paymentResult.errorMessage,
            )
            viewModelScope.launch {
                validationEventChannel.send(ValidatAllFieldsResultEvent.UpdateWrong(hasErrorList = hasErrorList))
            }
            return
        }
        viewModelScope.launch {
            if (validateFormState.id != 0) {
                updateClient(
                    Client(
                        id = validateFormState.id!!,
                        status = ClientStatus.waiting,
                        firstName = validateFormState.firstName.trim(),
                        secondName = validateFormState.secondName?.trim(),
                        lastName = validateFormState.lastName?.trim(),
                        phone = validateFormState.phone.trim(),
                        documentNumber = validateFormState.documentNamber!!.trim(),
                        documentDitails = validateFormState.documentDitails!!.trim(),
                        inDate = validateFormState.dateInLong,
                        outDate = validateFormState.dateOutLong,
                        members = validateFormState.members.trim().toInt(),
                        prepayment = validateFormState.prePayment.trim().toInt(),
                        payment = validateFormState.payment.trim().toInt(),
                        clientColor = validateFormState.color.toArgb(),
                        transferInfo = validateFormState.transferInfo,
                        referer = validateFormState.referer,
                        appatmentName = validateFormState.apartmentName!!
                    )
                )
            }
            validationEventChannel.send(ValidatAllFieldsResultEvent.UpdateSuccess)
        }
    }
}



