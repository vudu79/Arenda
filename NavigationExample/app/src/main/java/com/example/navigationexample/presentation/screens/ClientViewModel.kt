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
import com.example.navigationexample.domain.models.ClientStatus
import com.example.navigationexample.domain.usecase.validation.*
import com.example.navigationexample.domain.usecase.validation.validators.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


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

    var currentApartment = MutableLiveData<Appatment>()


    var validateFormState by mutableStateOf(ValidationFormState())
    private val validationEventChannel = Channel<ValidatAllFieldsResultEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    init {

    }


    fun addClient(client: Client) {
        clientRepository.insertClient(client)
        daysRepository.insertClientDays(client)
    }

    fun getClientByPhone(phone: String): Client{

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
        val membersResult =membersValidationField.execute(validateFormState.members)
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
             membersError=membersResult.errorMessage,
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

            addClient(
                Client(
                    status = ClientStatus.waiting,
                    firstName = validateFormState.firstName.trim(),
                    secondName = validateFormState.secondName?.trim(),
                    lastName = validateFormState.lastName?.trim(),
                    phone = "+7${validateFormState.phone.trim()}",
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
                    appatment_name = currentApartment.value?.name ?: ""
                )
            )
            validationEventChannel.send(ValidatAllFieldsResultEvent.Success)
        }
    }
}



