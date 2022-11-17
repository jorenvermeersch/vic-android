package com.example.vic.screens.customerlist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.database.entities.CustomerIndex

@BindingAdapter("name")
fun TextView.setName(item: CustomerIndex?) {
    item?.let {
        text = item.name
    }
}

