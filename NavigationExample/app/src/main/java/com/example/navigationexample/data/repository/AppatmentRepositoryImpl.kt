package com.example.navigationexample.data.repository

import androidx.lifecycle.LiveData
import com.example.navigationexample.data.dao.AppatmentDao
import com.example.navigationexample.data.entity.Appatment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class AppatmentRepositoryImpl @Inject constructor(private val appatmentDao: AppatmentDao) {

    val allAppatment: LiveData<List<Appatment>> = appatmentDao.getAllAppatment()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertAppatment(newappatment: Appatment) {
        coroutineScope.launch(Dispatchers.IO) {
            appatmentDao.insertAppatment(newappatment)
        }
    }

    fun deleteAppatment(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            appatmentDao.deleteAppatment(name)
        }
    }

//    fun findProduct(name: String) {
//        coroutineScope.launch(Dispatchers.Main) {
//            searchResults.value = asyncFind(name).await()
//        }
//    }
//
//    private fun asyncFind(name: String): Deferred<List<Product>?> =
//        coroutineScope.async(Dispatchers.IO) {
//            return@async productDao.findProduct(name)
//        }

}