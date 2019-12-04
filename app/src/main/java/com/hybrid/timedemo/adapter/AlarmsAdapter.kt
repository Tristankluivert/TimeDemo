package com.hybrid.timedemo.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.hybrid.timedemo.R
import com.hybrid.timedemo.database.Med
import com.hybrid.timedemo.helpers.config
import com.hybrid.timedemo.helpers.getFormattedTime
import com.simplemobiletools.commons.adapters.MyRecyclerViewAdapter
import com.simplemobiletools.commons.dialogs.ConfirmationDialog
import com.simplemobiletools.commons.extensions.*
import com.simplemobiletools.commons.views.MyRecyclerView
import kotlinx.android.synthetic.main.item_alarm.view.*
import java.util.*

class AlarmsAdapter(private val mDataList: ArrayList<Med>) : RecyclerView.Adapter<AlarmsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.tv_login.text = mDataList[position].login
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //internal var tv_login: TextView

        init {
            //tv_login = itemView.findViewById<View>(R.id.tv_login) as TextView


        }
    }
}