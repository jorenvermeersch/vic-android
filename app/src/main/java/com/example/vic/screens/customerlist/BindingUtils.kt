package com.example.vic.screens.customerlist

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.database.entities.CustomerIndex

@BindingAdapter("name")
fun TextView.setName(item: CustomerIndex?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("indicator")
fun ImageView.setIndicator(item: CustomerIndex?) {
    item?.let {
        if (item.isActive) {
            setColorFilter(R.color.blue)
        } else {
            setColorFilter(R.color.dark_gray)
        }
    }
}
