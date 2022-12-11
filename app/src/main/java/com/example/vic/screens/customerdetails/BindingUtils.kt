package com.example.vic.screens.customerdetails

import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.domain.entities.VirtualMachineIndex
import com.example.vic.domain.enums.Status

@BindingAdapter("name")
fun TextView.setName(item: VirtualMachineIndex?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("indicator")
fun TextView.setIndicator(item: VirtualMachineIndex?) {
    item?.let {
        val typedValue = TypedValue()
        val ref =
            if (item.status == Status.Deployed) R.attr.activeIndicatorColor else R.attr.inactiveIndicatorColor
        context.theme.resolveAttribute(ref, typedValue, true)
        val color = typedValue.data

        background.setTint(color)
    }
}