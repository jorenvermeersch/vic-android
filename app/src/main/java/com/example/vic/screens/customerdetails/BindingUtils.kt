package com.example.vic.screens.customerdetails

import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.database.entities.VirtualMachineIndex
import com.example.vic.database.enums.Status

@BindingAdapter("name")
fun TextView.setName(item: VirtualMachineIndex?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("indicator")
fun TextView.setIndicator(item: VirtualMachineIndex?) {
    item?.let {
        var color =
            if (item.status == Status.Deployed) R.attr.activeIndicatorColor else R.attr.inactiveIndicatorColor
        DrawableCompat.setTint(background, color)
    }
}