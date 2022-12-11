package com.example.vic.screens.customerlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vic.database.CustomerIndexDatabaseDao

class CustomerListViewModelFactory(private val dataSource: CustomerIndexDatabaseDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CustomerListViewModel::class.java)) {
            return CustomerListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

