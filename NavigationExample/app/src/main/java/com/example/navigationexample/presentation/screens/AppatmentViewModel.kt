package com.example.navigationexample.presentation.screens

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.AppatmentRoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.repository.AppatmentRepositoryImpl


class AppatmentViewModel(application: Application) : ViewModel() {

    val allAppatments: LiveData<List<Appatment>>

    private val repository: AppatmentRepositoryImpl


    init {
        val appatmentDb = AppatmentRoomDatabase.getInstance(application)
        val appatmentDao = appatmentDb.appatmentDao()
        repository = AppatmentRepositoryImpl(appatmentDao = appatmentDao)

        allAppatments = repository.allAppatment

    }

    fun insertAppatment(appatment: Appatment) {
        repository.insertAppatment(appatment)
    }

    fun deleteAppatment(name: String) {
        repository.deleteAppatment(name)
    }

}