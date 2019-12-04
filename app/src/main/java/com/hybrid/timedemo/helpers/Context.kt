package com.hybrid.timedemo.helpers

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.PowerManager
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import com.hybrid.timedemo.*
import com.hybrid.timedemo.database.Med

import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.ALARM_SOUND_TYPE_ALARM
import com.simplemobiletools.commons.helpers.isLollipopPlus
import com.simplemobiletools.commons.helpers.isOreoPlus
import java.util.*
import kotlin.math.pow

val Context.config: Config get() = Config.newInstance(applicationContext)


fun Context.checkAlarmsWithDeletedSoundUri(uri: String) {
    val defaultAlarmSound = getDefaultAlarmSound(ALARM_SOUND_TYPE_ALARM)
   /* dbHelper.getAlarmsWithUri(uri).forEach {
        it.soundTitle = defaultAlarmSound.title
        it.soundUri = defaultAlarmSound.uri
        dbHelper.updateAlarm(it)*/
    }

fun Context.getFormattedTime(passedSeconds: Int, showSeconds: Boolean, makeAmPmSmaller: Boolean): SpannableString {
    val use24HourFormat = config.use24HourFormat
    val hours = (passedSeconds / 3600) % 24
    val minutes = (passedSeconds / 60) % 60
    val seconds = passedSeconds % 60

    return if (!use24HourFormat) {
        val formattedTime = formatTo12HourFormat(showSeconds, hours, minutes, seconds)
        val spannableTime = SpannableString(formattedTime)
        val amPmMultiplier = if (makeAmPmSmaller) 0.4f else 1f
        spannableTime.setSpan(RelativeSizeSpan(amPmMultiplier), spannableTime.length - 5, spannableTime.length, 0)
        spannableTime
    } else {
        val formattedTime = formatTime(showSeconds, use24HourFormat, hours, minutes, seconds)
        SpannableString(formattedTime)
    }
}

fun Context.createNewAlarm(timeInMinutes: Int, weekDays: Int): Med {
    val defaultAlarmSound = getDefaultAlarmSound(ALARM_SOUND_TYPE_ALARM)
    return Med(0, timeInMinutes, weekDays, false, false, defaultAlarmSound.title, defaultAlarmSound.uri, "")
}

fun Context.formatTo12HourFormat(showSeconds: Boolean, hours: Int, minutes: Int, seconds: Int): String {
    val appendable = getString(if (hours >= 12) R.string.p_m else R.string.a_m)
    val newHours = if (hours == 0 || hours == 12) 12 else hours % 12
    return "${formatTime(showSeconds, false, newHours, minutes, seconds)} $appendable"
}


fun Context.isScreenOn() = (getSystemService(Context.POWER_SERVICE) as PowerManager).isScreenOn
fun Context.showRemainingTimeMessage(totalMinutes: Int) {
    val fullString = String.format(getString(R.string.alarm_goes_off_in), formatMinutesToTimeString(totalMinutes))
    toast(fullString, Toast.LENGTH_LONG)
}


fun Context.getReminderActivityIntent(): PendingIntent {
    val intent = Intent(this, MainActivity::class.java)
    return PendingIntent.getActivity(this, REMINDER_ACTIVITY_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

fun Context.hideNotification(id: Int) {
    val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.cancel(id)
}



fun Context.getOpenAlarmTabIntent(): PendingIntent {
    val intent = Intent(this, MainActivity::class.java)
    intent.putExtra(OPEN_TAB, TAB_ALARM)
    return PendingIntent.getActivity(this, OPEN_ALARMS_TAB_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}