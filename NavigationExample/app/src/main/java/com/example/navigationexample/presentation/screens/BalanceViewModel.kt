package com.example.navigationexample.presentation.screens


import androidx.compose.foundation.gestures.DraggableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Apartment
import com.example.navigationexample.data.repository.ApartmentRepositoryImpl
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val clientRepository: ClientsRepositoryImpl,
    private val apartmentRepository: ApartmentRepositoryImpl,
) : ViewModel() {


    //    Переменные для вкладок периодов выборки
    private val _tabIndexPeriod: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndexPeriod: LiveData<Int> = _tabIndexPeriod
    val tabsPeriod = listOf("День", "Неделя", "Месяц", "Год", "Период")

    var isSwipeToTheLeftPeriod: Boolean = false
    private val draggableStatePeriod = DraggableState { delta ->
        isSwipeToTheLeftPeriod = delta > 0
    }

    private val _dragStatePeriod = MutableLiveData<DraggableState>(draggableStatePeriod)
    val dragStatePeriod: LiveData<DraggableState> = _dragStatePeriod


    fun updateTabIndexBasedOnSwipePeriod() {
        _tabIndexPeriod.value = when (isSwipeToTheLeftPeriod) {
            true -> Math.floorMod(_tabIndexPeriod.value!!.plus(1), tabsPeriod.size)
            false -> Math.floorMod(_tabIndexPeriod.value!!.minus(1), tabsPeriod.size)
        }
    }

    fun updateTabIndexPeriod(i: Int) {
        _tabIndexPeriod.value = i
    }


    //    переменные для вкладок расходы и доходы
    private val _tabIndexExpenses: MutableLiveData<Int> = MutableLiveData(0)
    val tabIndexExpenses: LiveData<Int> = _tabIndexExpenses
    val tabsExpenses = listOf("Доходы", "Расходы")

    var isSwipeToTheLeftExpenses: Boolean = false
    private val draggableStateExpenses = DraggableState { delta ->
        isSwipeToTheLeftExpenses = delta > 0
    }

    private val _dragStateExpenses = MutableLiveData<DraggableState>(draggableStateExpenses)
    val dragStateExpenses: LiveData<DraggableState> = _dragStateExpenses
    fun updateTabIndexBasedOnSwipeExpenses() {
        _tabIndexExpenses.value = when (isSwipeToTheLeftExpenses) {
            true -> Math.floorMod(_tabIndexExpenses.value!!.plus(1), tabsExpenses.size)
            false -> Math.floorMod(_tabIndexExpenses.value!!.minus(1), tabsExpenses.size)
        }
    }

    fun updateTabIndexExpenses(i: Int) {
        _tabIndexExpenses.value = i
    }

    //    функционал для блока с выбором аппартаментов

    fun getAllApartments(){
        apartmentRepository.getAllApartments()
    }

    var allApartments: LiveData<List<Apartment>>

    private var _currentApartment: MutableLiveData<String> = MutableLiveData()
    val currentApartment: LiveData<String> = _currentApartment

    init {
        allApartments = apartmentRepository.allApartmentsLD
        _currentApartment = apartmentRepository.currentApartment
    }

//    обновление счетов при апдейте оплаты клиента

    fun clientScoresUpdate(){

    }

}
