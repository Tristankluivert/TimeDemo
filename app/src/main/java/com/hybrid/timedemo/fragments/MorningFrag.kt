package com.hybrid.timedemo.fragments

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hybrid.timedemo.R
import com.hybrid.timedemo.adapter.AlarmsAdapter
import com.hybrid.timedemo.database.Med
import com.hybrid.timedemo.dialog.EditAlarm
import com.hybrid.timedemo.helpers.config
import com.simplemobiletools.commons.extensions.addBit
import com.simplemobiletools.commons.extensions.moveLastItemToFront
import com.simplemobiletools.commons.extensions.removeBit
import kotlinx.android.synthetic.main.dialog_edit_alarm.*
import kotlinx.android.synthetic.main.dialog_edit_alarm.view.*
import kotlinx.android.synthetic.main.item_alarm.*
import kotlinx.android.synthetic.main.item_alarm.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class MorningFrag : Fragment() {

    private var alarms = ArrayList<Med>()
    private var currentEditAlarmDialog: EditAlarm? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: AlarmsAdapter
    lateinit var time: TextView
    lateinit var switchCompat: SwitchCompat
    lateinit var drugname: EditText
    lateinit var sound: TextView
    lateinit var fabmorning: FloatingActionButton
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val   root = inflater.inflate(R.layout.fragment_morning, container, false)
        fabmorning = root.findViewById(R.id.fabmorning)

        fabmorning.setOnClickListener {

            openAlarm()
        }

        return root
    }

    fun openAlarm(){

        val mDialogView = LayoutInflater.from(context!!).inflate(R.layout.dialog_edit_alarm, null)
        drugname = mDialogView.findViewById(R.id.edit_alarm_label)
        switchCompat = mDialogView.findViewById(R.id.edit_alarm_vibrate)
        time = mDialogView.findViewById(R.id.edit_alarm_time)



        val mBuilder = AlertDialog.Builder(context!!)
            .setView(mDialogView)
            .setPositiveButton("YES",DialogInterface.OnClickListener { dialogInterface, i ->


            })
            .setNegativeButton("NO",DialogInterface.OnClickListener { dialogInterface, i ->


            })

        mDialogView.edit_alarm_time.setOnClickListener {
            val now = Calendar.getInstance()

            try {
                if (alarm_time.text != "7:00"){
                    val date = timeFormat.parse(alarm_time.text.toString())
                    now.time = date
                }
            }catch (e : Exception){
                e.printStackTrace()
            }

            val mpicker = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,i)
                selectedTime.set(Calendar.MINUTE,i2)
                alarm_time.text = timeFormat.format(selectedTime.time)

            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false
            )
            mpicker.show()

        }

        mDialogView.edit_alarm_vibrate.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {


            } else {



            }

        }

       /* val dayLetters =
            activity!!.resources.getStringArray(R.array.week_day_letters).toList() as ArrayList<String>
        val dayIndexes = arrayListOf(0, 1, 2, 3, 4, 5, 6)
        if (activity!!.config.isSundayFirst) {
            dayIndexes.moveLastItemToFront()
        }

        dayIndexes.forEach {
            val pow = Math.pow(2.0, it.toDouble()).toInt()
            val day = activity!!.layoutInflater.inflate(
                R.layout.alarm_day,
                edit_alarm_days_holder,
                false
            ) as TextView
            day.text = dayLetters[it]
              val med = Med
            val isDayChecked = med.days and pow != 0
            // day.background = getProperDayDrawable(isDayChecked)
            val textColor = Color.BLUE
            day.setTextColor(if (isDayChecked) context!!.config.backgroundColor else textColor)
            day.setOnClickListener {
                val selectDay = med.days and pow == 0
                if (selectDay) {
                    med.days = med.days.addBit(pow)
                } else {
                    med.days = med.days.removeBit(pow)
                }
                // day.background = getProperDayDrawable(selectDay)
                day.setTextColor(if (selectDay) context!!.config.backgroundColor else textColor)
            }

            edit_alarm_days_holder.addView(day)
        }*/


        val  mAlertDialog = mBuilder.show()



    }

  fun saveAlarm(){

  }

}