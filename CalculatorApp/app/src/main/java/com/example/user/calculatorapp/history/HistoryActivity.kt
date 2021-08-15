package com.example.user.calculatorapp.history

import android.app.ProgressDialog
import android.content.res.Configuration
import android.database.Cursor
import android.database.SQLException
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.user.calculatorapp.R
import com.example.user.calculatorapp.roomdatabase.History

import com.example.user.calculatorapp.roomdatabase.HistoryRoomDatabase


/**
 * Created by User on 06-08-2021.
 */
class HistoryActivity : AppCompatActivity() {
//    lateinit var cursor : Cursor
    private var history : List<History>? = null
    lateinit var historyRecyclerView : RecyclerView
    private var current_view = HISTORY_VIEW
    companion object {
        const val HISTORY_VIEW = "history_view"
        const val NO_HISTORY_VIEW = "no_history_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_no_data_found)

        getHistoryFromRooom()
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
    private fun getHistoryFromRooom(){
        history = GetAllHistory().execute().get()

    }
    private inner class GetAllHistory : AsyncTask<Unit,Unit,List<History>?>(){
        val dialog : ProgressDialog = ProgressDialog(this@HistoryActivity)
        override fun onPreExecute() {
            super.onPreExecute()
            dialog.setMessage("Processing....")
            dialog.show()
        }
        override fun doInBackground(vararg params: Unit?) : List<History>? {
            val historyRoomDatabase = HistoryRoomDatabase
            var c_history :List<History>? = listOf()
            try{
                c_history = historyRoomDatabase.getDatabase(baseContext)?.historyDao()?.getAllHistory()
            }catch(e:Exception){
                e.printStackTrace()
            }


            return c_history
        }

        override fun onPostExecute(result: List<History>?) {
            super.onPostExecute(result)
            dialog.dismiss()
        }
    }
     private fun clearContentProvider(){
         if(history?.size != 0){
             ClearHistory().execute()
         }
         current_view = NO_HISTORY_VIEW
         setScreenLayout()
         //finish()



    }
    private inner class ClearHistory : AsyncTask<Unit, Unit, Unit>(){
        override fun doInBackground(vararg params: Unit?) {
            val historyRoomDatabase = HistoryRoomDatabase
            try{
                val rowId = historyRoomDatabase.getDatabase(baseContext)?.historyDao()?.also {
                    it.clearHistory()
                    it.resetHistoryTable()
                }
            }catch (e :SQLException){
                e.printStackTrace()
            }

        }

    }
}