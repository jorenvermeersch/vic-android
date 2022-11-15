package com.example.vic.screens.customerlist

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.database.entities.CustomerIndex
import timber.log.Timber


@BindingAdapter("name")
fun TextView.setName(item: CustomerIndex?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("indicator")
fun ImageView.setIndicator(item: CustomerIndex?) {
    // TODO: Does not work. Does not support themes with R.attr.
    item?.let {
        setColorFilter(
            if (item.isActive) R.attr.activeIndicatorColor else R.attr.inactiveIndicatorColor
        )

    }
}
