package com.hybrid.timedemo.dialog

import android.app.Activity
import android.app.TimePickerDialog
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.widget.TextView
import com.hybrid.timedemo.R
import com.hybrid.timedemo.database.Med
import com.hybrid.timedemo.helpers.*
import com.simplemobiletools.commons.activities.BaseSimpleActivity
import com.simplemobiletools.commons.dialogs.SelectAlarmSoundDialog
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.helpers.ALARM_SOUND_TYPE_ALARM
import com.simplemobiletools.commons.models.AlarmSound
import kotlinx.android.synthetic.main.dialog_edit_alarm.view.*

class EditAlarm(val activity: BaseSimpleActivity,  val med: Med, val callback: () -> Unit) {
    private val view = activity.layoutInflater.inflate(R.layout.dialog_edit_alarm, null)
    private val textColor = activity.config.textColor


    init {
        updateAlarmTime()

        view.apply {
            edit_alarm_time.setOnClickListener {

            }

            // edit_alarm_sound.colorLeftDrawable(textColor)
            edit_alarm_sound.text = med.soundTitle
            edit_alarm_sound.setOnClickListener {
                SelectAlarmSoundDialog(
                    activity,
                    med.soundUri,
                    AudioManager.STREAM_ALARM,
                    PICK_AUDIO_FILE_INTENT_ID,
                    ALARM_SOUND_TYPE_ALARM,
                    true,
                    onAlarmPicked = {
                        if (it != null) {
                             updateSelectedAlarmSound(it)
                        }
                    },
                    onAlarmSoundDeleted = {
                        if (med.soundUri == it.uri) {
                            val defaultAlarm = context.getDefaultAlarmSound(ALARM_SOUND_TYPE_ALARM)
                            updateSelectedAlarmSound(defaultAlarm)
                        }
                        activity.checkAlarmsWithDeletedSoundUri(it.uri)
                    })
            }

             edit_alarm_vibrate.colorLeftDrawable(textColor)
            edit_alarm_vibrate.isChecked = med.vibrate
            edit_alarm_vibrate_holder.setOnClickListener {
                edit_alarm_vibrate.toggle()
                med.vibrate = edit_alarm_vibrate.isChecked
            }

            edit_alarm_label_image.applyColorFilter(textColor)
            edit_alarm_label.setText(med.drugname)

            val dayLetters =
                activity.resources.getStringArray(R.array.week_day_letters).toList() as ArrayList<String>
            val dayIndexes = arrayListOf(0, 1, 2, 3, 4, 5, 6)
            if (activity.config.isSundayFirst) {
                dayIndexes.moveLastItemToFront()
            }

            dayIndexes.forEach {
                val pow = Math.pow(2.0, it.toDouble()).toInt()
                val day = activity.layoutInflater.inflate(
                    R.layout.alarm_day,
                    edit_alarm_days_holder,
                    false
                ) as TextView
                day.text = dayLetters[it]

                val isDayChecked = med.days and pow != 0
                   day.background = getProperDayDrawable(isDayChecked)

                 day.setTextColor(if (isDayChecked) context.config.backgroundColor else textColor)
                day.setOnClickListener {
                    val selectDay = med.days and pow == 0
                    if (selectDay) {
                        med.days = med.days.addBit(pow)
                    } else {
                        med.days = med.days.removeBit(pow)
                    }
                    day.background = getProperDayDrawable(selectDay)
                     day.setTextColor(if (selectDay) context.config.backgroundColor else textColor)
                }

                edit_alarm_days_holder.addView(day)
            }
        }
    }
    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        med.timeInMinutes = hourOfDay * 60 + minute
        updateAlarmTime()
    }

    private fun updateAlarmTime() {
        view.edit_alarm_time.text = activity.getFormattedTime(med.timeInMinutes * 60, false, true)
    }

    private fun getProperDayDrawable(selected: Boolean): Drawable {
        val drawableId = if (selected) R.drawable.circle_background_filled else R.drawable.circle_background_stroke
        val drawable = activity.resources.getDrawable(drawableId)
        //drawable.applyColorFilter(textColor)
        return drawable
    }

    fun updateSelectedAlarmSound(alarmSound: AlarmSound) {
        med.soundTitle = alarmSound.title
        med.soundUri = alarmSound.uri
        view.edit_alarm_sound.text = alarmSound.title
    }
}


