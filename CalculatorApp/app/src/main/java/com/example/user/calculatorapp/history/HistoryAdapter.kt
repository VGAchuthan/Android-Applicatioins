package com.example.user.calculatorapp.history

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.user.calculatorapp.R
import kotlinx.android.synthetic.main.layout_result_view.view.*

/**
 * Created by User on 06-08-2021.
 */
class HistoryAdapter(context : Context, cursor : Cursor) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val mContext = context
    private val mCursor = cursor
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OperationHistoryViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.layout_operation_history_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mCursor.count
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(!mCursor.moveToPosition(position)){
            return
        }
        (holder as OperationHistoryViewHolder).bind(position)
    }
    fun swapCursor(newCursor: Cursor){
        if(mCursor != null){
            mCursor.close()
        }
       // mCursor = newCursor
    }
    private inner class OperationHistoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val operationView = itemView.findViewById<TextView>(R.id.history_view_operation)

        val result = itemView.findViewById<TextView>(R.id.history_view_result)
        fun bind(position : Int){
            //mCursor.
            //var historyCursor  : Cursor = mCursor.moveToPosition(position)
             var operation = mCursor.getString(mCursor.getColumnIndex("action"))
            var input1 =mCursor.getString(mCursor.getColumnIndex("input1"))
            var input2 = mCursor.getString(mCursor.getColumnIndex("input2"))
            var symbol : Char = when(operation){
                "ADD" -> { '+' }
                "SUB" -> { '-' }
                "MULTIPLY" -> {'*'}
                else -> {'/'}
            }
            operationView.text = "$input1 $symbol $input2"

            result.text = mCursor.getString(mCursor.getColumnIndex("result"))

        }
    }
}