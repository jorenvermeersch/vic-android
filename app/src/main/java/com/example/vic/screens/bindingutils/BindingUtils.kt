package com.example.vic.screens.bindingutils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.domain.entities.ContactPerson

// Binding adapter used in multiple fragments.
@BindingAdapter("fullName")
fun TextView.setFullName(contactPerson: ContactPerson?) {
    contactPerson?.let {
        text =
            String.format(resources.getString(R.string.format_full_name), it.firstName, it.lastName)
    }
}
