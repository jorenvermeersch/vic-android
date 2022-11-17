package com.example.vic.screens.customerdetails

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.database.entities.VirtualMachineIndex

@BindingAdapter("name")
fun TextView.setName(item: VirtualMachineIndex?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("indicator")
fun ImageView.setIndicator(item: VirtualMachineIndex?) {
    item?.let {
        setColorFilter(R.color.blue)
    }
}