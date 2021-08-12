package com.example.user.calculatorapp.history

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.calculatorapp.R
import com.example.user.calculatorapp.providers.MyHistoryProvider
import kotlinx.android.synthetic.main.fragment_operations_button.*

/**
 * Created by User on 06-08-2021.
 */
class HistoryActivity : AppCompatActivity() {
    lateinit var cursor : Cursor
    lateinit var historyRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var history = getHistoryFromContentProvider()
        var mActionBar = supportActionBar
        setTitle(R.string.title_histroy)
        mActionBar?.setDisplayHomeAsUpEnabled(true)
        if(history.count == 0 || history == null ){
            setContentView(R.layout.layout_no_data_found)
            return
        }
        setContentView(R.layout.activity_history)

        historyRecyclerView = findViewById<RecyclerView>(R.id.history_recyclerview)
        var params = historyRecyclerView.layoutParams
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.layoutParams = params
        var dividerItemDecoration = DividerItemDecoration(historyRecyclerView.context, Configuration.ORIENTATION_PORTRAIT)
//        historyRecyclerView.addItemDecoration
        var adapter = HistoryAdapter(this,getHistoryFromContentProvider())
//        getHistoryFromContentProvider()
        historyRecyclerView.adapter = adapter
        historyRecyclerView.addItemDecoration(dividerItemDecoration)
        historyRecyclerView.scrollToPosition(adapter.itemCount)
       // cursor.close()

    }

    private fun getHistoryFromContentProvider() : Cursor{
        cursor = contentResolver.query(Uri.parse("content://com.example.user.calculatorapp/operations"),null, null, null,"${MyHistoryProvider.id} DESC")

        //var localcursor = cursor
        //cursor.close()
        return cursor

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
        cursor.close()
        super.onDestroy()
    }
     fun clearContentProvider(){
         if(cursor.count != 0){
             val delete = contentResolver.delete(Uri.parse("content://com.example.user.calculatorapp/operations"),null, null)
             historyRecyclerView.adapter = null
         }
         finish()


    }
}