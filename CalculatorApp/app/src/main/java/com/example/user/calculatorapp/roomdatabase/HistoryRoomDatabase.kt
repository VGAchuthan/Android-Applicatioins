package com.example.user.calculatorapp.roomdatabase

import android.content.Context
import android.database.SQLException
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

/**
 * Created by User on 12-08-2021.
 */
@Database(entities = arrayOf(History::class, Functions::class),version = 1,exportSchema = false)
abstract class HistoryRoomDatabase : RoomDatabase(){

    abstract fun historyDao() : HistoryDAO
    abstract fun functionDao() : FunctionsDAO
    companion object {
        const val OPERATIONS_TABLE = "operations"
        const val FUNCTIONS_TABLE = "functions"
        private var INSTANCE : HistoryRoomDatabase? =null


        fun getDatabase(context: Context) : HistoryRoomDatabase?{
            if(INSTANCE == null){
                synchronized(HistoryRoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            HistoryRoomDatabase::class.java,
                            "calculator.db"
                    ).addCallback( object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val listOfFunctions = DefaultFunctions.getFunctionList()
                            Executors.newSingleThreadExecutor().execute{
                                INSTANCE?.let {
                                    for(function in listOfFunctions){
                                        try{
                                            println(it?.functionDao().insertFunction(function))
                                        }
                                        catch(e: SQLException){
                                            e.printStackTrace()
                                        }

                                    }

                                }
                            }
                        }

                    }

                    ).build()
                }
            }

            return INSTANCE
        }
    }
//    val callBack =
}
object DefaultFunctions{
//    private val listOfFunctions = listof()
    fun getFunctionList() : List<Functions>{
        return listOf(
                Functions(action = "ADDITION"),
                Functions(action = "SUBTRACTION"),
                Functions(action = "MULTIPLICATION"),
                Functions(action = "DIVISION")
        )

    }
}