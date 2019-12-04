package com.hybrid.timedemo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MedDao {

    @Insert
    fun addMed(med: Med)

    @Query("SELECT *FROM med ")
    fun getMed() : List<Med>

    @Insert
    fun addMoreMed(vararg med: Med)
}