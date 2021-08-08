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
import kotlinx.android.synthetic.main.fragment_operations_button.*

/**
 * Created by User on 06-08-2021.
 */
class HistoryActivity : AppCompatActivity() {
    lateinit var cursor : Cursor
    lateinit var historyRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        var mActionBar = supportActionBar
//        val toolbar = findViewById<Toolbar>(R.id.main_activity_toolbar)
//        setActionBar(toolbar)
        historyRecyclerView = findViewById<RecyclerView>(R.id.history_recyclerview)

        setTitle(R.string.title_histroy)
        mActionBar?.setDisplayHomeAsUpEnabled(true)



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
       // cursor.close()

    }

    private fun getHistoryFromContentProvider() : Cursor{
        cursor = contentResolver.query(Uri.parse("content://com.example.user.calculatorapp/operations"),null, null, null,null)
        if(cursor == null){
            Log.e("HISTORY ACTIVITY","CURSOR NULL")
        }
        else{
            if(cursor!!.moveToFirst()){
                while(!cursor.isAfterLast){
                    val action = cursor.getString(cursor.getColumnIndex("action"))
                    val input1 = cursor.getString(cursor.getColumnIndex("input1"))
                    val input2 = cursor.getString(cursor.getColumnIndex("input2"))
                    val result = cursor.getString(cursor.getColumnIndex("result"))
                    println("$action - $input1 - $input2 - $result")
                    cursor.moveToNext()
                }
            }
        }
        //var localcursor = cursor
        //cursor.close()
        return cursor

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //val menuInflater =
        menuInflater.inflate(R.menu.menu_history_activity, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?) = when(item?.itemId){
        android.R.id.home ->{
            this.finish()
            true
        }
        R.id.menu_action_clear ->{
            clearContentProvider()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        cursor.close()
        super.onDestroy()
    }
    private fun clearContentProvider(){
        val delete = contentResolver.delete(Uri.parse("content://com.example.user.calculatorapp/operations"),null, null)
        historyRecyclerView.adapter = null

    }
}