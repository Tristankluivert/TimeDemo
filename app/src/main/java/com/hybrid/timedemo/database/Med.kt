package com.hybrid.timedemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Med(
   @PrimaryKey(autoGenerate = true)
   val id: Int,
   var timeInMinutes: Int, var days: Int, var isEnabled: Boolean, var vibrate: Boolean, var soundTitle: String,
   var soundUri: String, var drugname: String

)