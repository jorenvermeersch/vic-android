package com.example.vic

import com.example.vic.domain.enums.BackupFrequency
import com.example.vic.domain.enums.Mode
import com.example.vic.domain.enums.Status
import com.example.vic.domain.enums.Template
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun translateMode(mode: Mode): Int {
    return when (mode) {
        Mode.SAAS -> R.string.mode_saas
        Mode.PAAS -> R.string.mode_paas
        Mode.IAAS -> R.string.mode_iaas
    }
}

fun translateTemplate(template: Template): Int {
    return when (template) {
        Template.NoTemplate -> R.string.template_no_template
        Template.AI -> R.string.template_ai
        Template.WebServer -> R.string.template_web_server
    }
}

fun translateBackupFrequency(backupFrequency: BackupFrequency): Int {
    return when (backupFrequency) {
        BackupFrequency.NoBackup -> R.string.backup_no_backup
        BackupFrequency.Daily -> R.string.backup_daily
        BackupFrequency.Weekly -> R.string.backup_weekly
        BackupFrequency.Monthly -> R.string.backup_monthly
    }
}

fun translateStatus(status: Status): Int {
    return when (status) {
        Status.Requested -> R.string.status_requested
        Status.InProgress -> R.string.status_in_progress
        Status.ReadyToDeploy -> R.string.status_ready_to_deploy
        Status.Deployed -> R.string.status_deployed
    }
}
