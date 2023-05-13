package com.example.navigationexample.presentation.screens

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
import androidx.lifecycle.viewModelScope
import com.example.navigationexample.domain.usecase.validation.*
import com.example.navigationexample.domain.usecase.validation.validators.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate


@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientsRepositoryImpl,

    private val nameValidationField: NameValidation = NameValidation(),
    private val phoneValidationField: PhoneValidation = PhoneValidation(),
    private val documentNumberValidationField: DocumentNumber = DocumentNumber(),
    private val documentDitailsValidationField: DocumentDitails = DocumentDitails(),
    private val membersValidationField: MembersValidation = MembersValidation(),
    private val paymentValidationField: PaymentValidation = PaymentValidation(),
    private val dateStringValidationField: DateStringValidation = DateStringValidation(),
    private val dateLongValidationField: DateLongValidation = DateLongValidation()
) : ViewModel() {

    var currentApartment = MutableLiveData<Appatment>()

    var validateFormState by mutableStateOf(ValidationFormState())

    private val validationEventChannel = Channel<ValidatAllFieldsResultEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()


    fun getClientState(phone: String) = viewModelScope.launch {
        val client = clientRepository.getClientByPhone(phone)
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
        validateFormState = validateFormState.copy(prePayment = client.prepayment.toString())
        validateFormState = validateFormState.copy(transferInfo = client.transferInfo)
        validateFormState = validateFormState.copy(referer = client.referer)
        validateFormState = validateFormState.copy(color = Color(client.clientColor))
    }

    fun updateClient(client: Client): Int {
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
//                Log.d("myTag", "asasd --- ${event.members}")
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
//                Log.d("myTag", "asasd --- ${event.prepayment}")
                validateFormState = validateFormState.copy(prePayment = event.prepayment)
            }
            is ValidationFormEvent.PaymentChanged -> {
//                Log.d("myTag", "asasd --- ${event.payment}")
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

//            updateClient(
//                Client(
//                    status = ClientStatus.waiting,
//                    firstName = validateFormState.firstName.trim(),
//                    secondName = validateFormState.secondName?.trim(),
//                    lastName = validateFormState.lastName?.trim(),
//                    phone = "+7${validateFormState.phone.trim()}",
//                    documentNumber = validateFormState.documentNamber!!.trim(),
//                    documentDitails = validateFormState.documentDitails!!.trim(),
//                    inDate = validateFormState.dateInLong,
//                    outDate = validateFormState.dateOutLong,
//                    members = validateFormState.members.trim().toInt(),
//                    prepayment = validateFormState.prePayment.trim().toInt(),
//                    payment = validateFormState.payment.trim().toInt(),
//                    clientColor = validateFormState.color.toArgb(),
//                    transferInfo = validateFormState.transferInfo,
//                    referer = validateFormState.referer,
//                    appatment_name = currentApartment.value?.name ?: ""
//                )
//            )
            validationEventChannel.send(ValidatAllFieldsResultEvent.Success)
        }
    }
}


