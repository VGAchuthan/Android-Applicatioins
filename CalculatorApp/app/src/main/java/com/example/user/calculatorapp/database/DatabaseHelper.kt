//package com.example.user.calculatorapp.database
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import android.util.Log
//import com.example.user.calculatorapp.providers.MyHistoryProvider
//
///**
// * Created by User on 11-08-2021.
// */
//class DatabaseHelper  // defining a constructor
//internal constructor(context: Context?) : SQLiteOpenHelper(
//        context,
//        DATABASE_NAME,
//        null,
//        DATABASE_VERSION
//) {
//
//    // creating a table in the database
//    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(CREATE_OPERATION_TABLE)
//        db.execSQL(CREATE_FUNCTIONS_TABLE)
//        Log.e("DB","ON CREATE")
////        db.execSQL(FUNCTION_TABLE_VALUES)
//        insertFunctionValues(db)
//        //db.insert(FUNCTIONS_TABLE_NAME,null, ContentValues().put("action","ADD"))
//
//    }
//
//    override fun onUpgrade(
//            db: SQLiteDatabase,
//            oldVersion: Int,
//            newVersion: Int
//    ) {
//        Log.e("DB","ON UPGRADE")
//        // sql query to drop a table
//        // having similar name
//        db.execSQL("DROP TABLE IF EXISTS $OPERATION_TABLE_NAME")
//        db.execSQL("DROP TABLE IF EXISTS $FUNCTIONS_TABLE_NAME")
//        onCreate(db)
//    }
//    private fun insertFunctionValues(wb: SQLiteDatabase){
////        val wb =
//        val addValues = ContentValues()
//        addValues.put("action","ADDITION")
//        wb.insert(FUNCTIONS_TABLE_NAME,null,addValues)
//        val subValues = ContentValues()
//        subValues.put("action","SUBTRACTION")
//        wb.insert(FUNCTIONS_TABLE_NAME,null,subValues)
//        val mulValues = ContentValues()
//        mulValues.put("action","MULTIPLICATION")
//        wb.insert(FUNCTIONS_TABLE_NAME,null,mulValues)
//        val divValues = ContentValues()
//        divValues.put("action","DIVISION")
//        wb.insert(FUNCTIONS_TABLE_NAME,null,divValues)
//        Log.e("db","values inserted")
//      //  wb.close()
//
//    }
//    companion object {
//        const val DATABASE_NAME = "CalculatorDB"
//
//        // declaring table name of the database
//        const val OPERATION_TABLE_NAME = "Operations"
//        const val FUNCTIONS_TABLE_NAME = "functions"
//        // declaring version of the database
//        const val DATABASE_VERSION = 1
//
//        // sql query to create the operation table
//        const val CREATE_OPERATION_TABLE =
//                (" CREATE TABLE " + OPERATION_TABLE_NAME
//                        + " (id INTEGER PRIMARY KEY AUTOINCREMENT, action TEXT NOT NULL,input1 TEXT NOT NULL,input2 TEXT NOT NULL,result TEXT NOT NULL);")
//
//        // sql query to create the functions table
//        const val CREATE_FUNCTIONS_TABLE =
//                (" CREATE TABLE " + FUNCTIONS_TABLE_NAME
//                        + " (id INTEGER PRIMARY KEY AUTOINCREMENT, action TEXT NOT NULL);")
//    }
//}