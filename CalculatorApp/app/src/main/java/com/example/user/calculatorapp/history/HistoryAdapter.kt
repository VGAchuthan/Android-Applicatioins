package com.example.user.calculatorapp.history

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.user.calculatorapp.R
import com.example.user.calculatorapp.roomdatabase.History
import kotlinx.android.synthetic.main.layout_result_view.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * Created by User on 06-08-2021.
 */

class HistoryAdapter(context : Context, history : List<History>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val mContext = context
    //private val mCursor = cursor
    private val _history = history
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    val today = LocalDate.now()
    val todayStartTime = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OperationHistoryViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.layout_operation_history_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return _history.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as OperationHistoryViewHolder).bind(position)
    }


    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {

        Log.e("VIEW RECYCLED","${holder.adapterPosition}")
        super.onViewRecycled(holder)
    }

    private inner class OperationHistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val operationView = itemView.findViewById<TextView>(R.id.history_view_operation)
        val dateView = itemView.findViewById<TextView>(R.id.history_view_date)
        val result = itemView.findViewById<TextView>(R.id.history_view_result)


       // val date : Int = Date().time.toInt()
        fun bind(position : Int){

            //mCursor.
            //var historyCursor  : Cursor = mCursor.moveToPosition(position)
             var operation = _history[position].action//= mCursor.getString(mCursor.getColumnIndex("action"))
            var input1  = _history[position].input1//=mCursor.getString(mCursor.getColumnIndex("input1"))
            var input2 =_history[position].input2//= mCursor.getString(mCursor.getColumnIndex("input2"))
            var symbol : Char = when(operation){
                "ADD" -> { '+' }
                "SUB" -> { '-' }
                "MULTIPLY" -> {'*'}
                else -> {'/'}
            }
            operationView.text = "$input1 $symbol $input2"
           val historyDate = Date(_history[position].date)
          // val historDateStartTime = Date.from()
           val historyLocalDate = historyDate.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
           val historyStartDate = Date.from(historyLocalDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant())
           if(historyDate.compareTo(todayStartTime) <= -1)
            dateView.text = "Yesterday"
           else if(historyStartDate.compareTo(todayStartTime) == 0)
               dateView.text = "Today"
           else
               dateView.text = dateFormat.format(historyDate)

            result.text = _history[position].result//" = "+mCursor.getString(mCursor.getColumnIndex("result"))

        }
    }
}