package com.example.user.calculatorapp.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


/**
 * Created by User on 12-08-2021.
 */
@Dao
interface HistoryDAO {
    @Insert
    fun insertHistory(history : History) : Long
    @Query("SELECT * FROM ${HistoryRoomDatabase.OPERATIONS_TABLE} ORDER BY id DESC")
    fun getAllHistory() : List<History>
    @Query("DELETE FROM ${HistoryRoomDatabase.OPERATIONS_TABLE}")
    fun clearHistory() : Int
    @Query("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME = '${HistoryRoomDatabase.OPERATIONS_TABLE}'")
    fun resetHistoryTable() : Int




}