package com.example.vic.screens.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vic.database.CustomerIndexDatabaseDao

class ApplicationViewModelFactory(
    private val dataSource: CustomerIndexDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationViewModel::class.java)) {
            return ApplicationViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
