package com.example.user.calculatorapp

import android.app.ProgressDialog
import android.content.Context
import android.database.SQLException
import android.os.AsyncTask
import androidx.room.RoomDatabase
import com.example.user.calculatorapp.history.HistoryActivity
import com.example.user.calculatorapp.roomdatabase.Functions
import com.example.user.calculatorapp.roomdatabase.History
import com.example.user.calculatorapp.roomdatabase.HistoryRoomDatabase

/**
 * Created by User on 16-08-2021.
 */
interface DBOperationsHelper{
    fun getAllHistory() : List<History>?
    fun insertHistory(history : History) : Long?
    fun clearHistory() : Int?
    fun getFunctions() : List<Functions>?


}
class DBOperations(context : Context) : DBOperationsHelper {
    private var _context : Context
    init{
        _context = context
    }

    private val roomDatabase = HistoryRoomDatabase
    override fun getAllHistory(): List<History>? {
        return GetAllHistory().execute().get()
    }

    override fun insertHistory(history: History): Long? {
        return InsertHistory(history).execute().get()

    }

    override fun clearHistory(): Int? {
        return ClearHistory().execute().get()

    }

    override fun getFunctions(): List<Functions>? {
        return GetFunctions().execute().get()

    }
    private inner class GetFunctions() : AsyncTask<Unit, Unit, List<Functions>?>(){
        override fun doInBackground(vararg params: Unit?) : List<Functions>?{
            var listOfFunction : List<Functions>? = listOf()
            try{
                listOfFunction = roomDatabase.getDatabase(_context)?.functionDao()?.getAllFunctions()
            }catch(e:Exception){
                e.printStackTrace()
            }
            return listOfFunction
        }
    }

    private inner class GetAllHistory : AsyncTask<Unit, Unit, List<History>?>() {


        override fun doInBackground(vararg params: Unit?): List<History>? {
            // val historyRoomDatabase = HistoryRoomDatabase
            var c_history: List<History>? = listOf()
            try {
                c_history = roomDatabase.getDatabase(_context)?.historyDao()?.getAllHistory()
            } catch (e: Exception) {
                e.printStackTrace()
            }


            return c_history
        }

    }

    private inner class ClearHistory : AsyncTask<Unit, Unit, Int?>() {
        override fun doInBackground(vararg params: Unit?): Int? {


            try {
                val rowId = roomDatabase.getDatabase(_context)?.historyDao()?.clearHistory()
                return rowId
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return -1

        }

    }

    private inner class InsertHistory(history: History) : AsyncTask<Unit, Unit, Long?>() {
        val _history = history
        override fun doInBackground(vararg params: Unit?): Long? {
//            for(i in 1..10000)
            try {
                val rowId = roomDatabase.getDatabase(_context)?.historyDao()?.insertHistory(_history)
                return rowId
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return -1


        }
    }

}