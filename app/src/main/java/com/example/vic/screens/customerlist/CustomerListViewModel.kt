package com.example.vic.screens.customerlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vic.database.CustomerIndexDatabaseDao


class CustomerListViewModel(val database: CustomerIndexDatabaseDao, app: Application): AndroidViewModel(app) {
    val jokes = database.getAll()
}