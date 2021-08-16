package com.example.user.calculatorapp.history


import android.content.res.Configuration

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.calculatorapp.DBOperations
import com.example.user.calculatorapp.DBOperationsHelper

import com.example.user.calculatorapp.R
import com.example.user.calculatorapp.roomdatabase.History



/**
 * Created by User on 06-08-2021.
 */
class HistoryActivity : AppCompatActivity() {
//    lateinit var cursor : Cursor
    private var history : List<History>? = null
    lateinit var historyRecyclerView : RecyclerView
    private var current_view = HISTORY_VIEW
    lateinit private var dbOperations: DBOperationsHelper
    companion object {
        const val HISTORY_VIEW = "history_view"
        const val NO_HISTORY_VIEW = "no_history_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_no_data_found)
        dbOperations = DBOperations(this.baseContext)

        history = getHistoryFromRooom()
        if(history?.size != 0)
            current_view = HISTORY_VIEW
        else
            current_view = NO_HISTORY_VIEW
        setScreenLayout()
        var mActionBar = supportActionBar
        setTitle(R.string.title_histroy)
        mActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    private fun setScreenLayout(){
        if(current_view.equals(NO_HISTORY_VIEW)){
            setContentView(R.layout.layout_no_data_found)
            return
        }
        else{
            setContentView(R.layout.activity_history)
            historyRecyclerView = findViewById<RecyclerView>(R.id.history_recyclerview)
            var params = historyRecyclerView.layoutParams
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            historyRecyclerView.layoutManager = LinearLayoutManager(this)
            historyRecyclerView.layoutParams = params
            var dividerItemDecoration = DividerItemDecoration(historyRecyclerView.context, Configuration.ORIENTATION_PORTRAIT)

            var adapter = HistoryAdapter(this,history!!)

            historyRecyclerView.adapter = adapter
            historyRecyclerView.addItemDecoration(dividerItemDecoration)
            historyRecyclerView.scrollToPosition(adapter.itemCount)
            // cursor.close()

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //val menuInflater =
        menuInflater.inflate(R.menu.menu_history_activity, menu)
        return true
    }
    fun onAlertPositiveButtonAction(){
        clearContentProvider()
    }


    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId){
        android.R.id.home ->{
            this.finish()
//            overridePend

            true
        }
        R.id.menu_action_clear ->{
            val clearanceAlertDialog = HistoryClearanceAlertDialog()
            clearanceAlertDialog.show(supportFragmentManager,HistoryClearanceAlertDialog.TAG)
            //clearContentProvider()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
//        cursor.close()
        super.onDestroy()
    }
    private fun getHistoryFromRooom() : List<History>?{
        return dbOperations.getAllHistory()

    }

     private fun clearContentProvider(){
         if(history?.size != 0){
             val rows = dbOperations.clearHistory()
             Log.e("ACTIVITY HISTORY"," ${rows} : ROWS AFFECTED")
         }
         current_view = NO_HISTORY_VIEW
         setScreenLayout()
         //finish()



    }

}