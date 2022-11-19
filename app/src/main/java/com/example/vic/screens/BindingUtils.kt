package com.example.vic.screens

import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.example.vic.*
import com.example.vic.database.entities.*
import com.example.vic.database.enums.BackupFrequency
import com.example.vic.database.enums.Mode
import com.example.vic.database.enums.Status
import com.example.vic.database.enums.Template
import java.util.*

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
@BindingAdapter("text")
fun TextView.setMode(mode: Mode?) {
    mode?.let {
        text = context.getString(translateMode(mode))
    }
}

@BindingAdapter("text")
fun TextView.setTemplate(template: Template?) {
    template?.let {
        text = context.getString(translateTemplate(template))
    }
}

@BindingAdapter("text")
fun TextView.setPorts(ports: List<Port>?) {
    var result = ""

    ports?.let {
        result = ports.joinToString(", ") { it.service }
    }

    text = result
}

@BindingAdapter("text")
fun TextView.setDate(date: Date?) {
    date?.let {
        text = date.toString("yyyy-MM-dd")
    }
}

@BindingAdapter("text")
fun TextView.setBackupFrequency(backupFrequency: BackupFrequency?) {
    backupFrequency?.let {
        text = context.getString(translateBackupFrequency(backupFrequency))
    }
}

@BindingAdapter("text")
fun TextView.setStatus(status: Status?) {
    status?.let {
        text = context.getString(translateStatus(status))
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


