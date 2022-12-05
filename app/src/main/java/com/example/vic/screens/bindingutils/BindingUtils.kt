package com.example.vic.screens.bindingutils

import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.vic.R
import com.example.vic.database.entities.Account
import com.example.vic.database.entities.ContactPerson
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine
import com.example.vic.database.entities.VirtualMachineIndex
import com.example.vic.database.enums.Status
import com.example.vic.toString
import com.example.vic.translateBackupFrequency
import com.example.vic.translateMode
import com.example.vic.translateStatus
import com.example.vic.translateTemplate
import java.util.Date

// customer_row.xml.
@BindingAdapter("name")
fun TextView.setName(item: CustomerIndex?) {
    item?.let {
        text = item.name
    }
}

// virtual_machine_row.xml.
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

// fragment_virtual_machine_details.xml.
@BindingAdapter("mode")
fun TextView.setMode(item: VirtualMachine?) {
    item?.let {
        text = context.getString(translateMode(item.mode))
    }
}

@BindingAdapter("template")
fun TextView.setTemplate(item: VirtualMachine?) {
    item?.let {
        text = context.getString(translateTemplate(item.template))
    }
}

@BindingAdapter("ports")
fun TextView.setPorts(item: VirtualMachine?) {
    var result = ""

    item?.let {
        result = item.ports.joinToString(", ") { it.service }
    }

    text = result
}

@BindingAdapter("date")
fun TextView.setDate(date: Date?) {
    date?.let {
        text = date.toString("yyyy-MM-dd")
    }
}

@BindingAdapter("backupFrequency")
fun TextView.setBackupFrequency(item: VirtualMachine?) {
    item?.let {
        text = context.getString(translateBackupFrequency(item.backupFrequency))
    }
}

@BindingAdapter("status")
fun TextView.setStatus(item: VirtualMachine?) {
    item?.let {
        text = context.getString(translateStatus(item.status))
    }
}

@BindingAdapter("indicator")
fun TextView.setIndicator(item: VirtualMachine?) {
    item?.let {
        val typedValue = TypedValue()
        val ref =
            if (item.status == Status.Deployed) R.attr.activeIndicatorColor else R.attr.inactiveIndicatorColor
        context.theme.resolveAttribute(ref, typedValue, true)
        val color = typedValue.data

        background.setTint(color)
    }
}

@BindingAdapter("fullName")
fun TextView.setFullName(contactPerson: ContactPerson?) {
    contactPerson?.let {
        text =
            String.format(resources.getString(R.string.format_full_name), it.firstName, it.lastName)
    }
}

@BindingAdapter("fullName")
fun TextView.setFullName(account: Account?) {
    account?.let {
        text =
            String.format(resources.getString(R.string.format_full_name), it.firstName, it.lastName)
    }
}
