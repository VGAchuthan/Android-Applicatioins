package com.example.user.calculatorapp.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * Created by User on 12-08-2021.
 */
@Entity(tableName = "${HistoryRoomDatabase.OPERATIONS_TABLE}")
data class History(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id : Int = 0,
        @ColumnInfo(name="action")
        val action : String,
        @ColumnInfo(name="input1")
        val input1:String,
        @ColumnInfo(name="input2")
        val input2:String,
        @ColumnInfo(name="result")
        val result:String
)

@Entity(tableName = "${HistoryRoomDatabase.FUNCTIONS_TABLE}")
data class  Functions(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id : Int =0,
        @ColumnInfo(name = "action")
        val action: String

)