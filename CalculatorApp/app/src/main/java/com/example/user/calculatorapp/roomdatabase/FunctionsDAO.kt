package com.example.user.calculatorapp.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by User on 13-08-2021.
 */
@Dao
interface FunctionsDAO {
    @Insert
    fun insertFunction(functions: Functions) : Long
    @Query("SELECT * FROM ${HistoryRoomDatabase.FUNCTIONS_TABLE}")
    fun getAllFunctions() : List<Functions>
}