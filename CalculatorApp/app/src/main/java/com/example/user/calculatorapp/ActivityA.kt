package com.example.user.calculatorapp

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.user.calculatorapp.enums.OperationType

class ActivityA : AppCompatActivity() {
    companion object {
        //0 -> For Operation button view
        // 1 -> For Result View
        var VIEW_MODE : Int = 0
        var RESULT_STRING : String = ""
    }
    private val REQUEST_CODE_FOR_ACTIVTY_B = 1

    lateinit var resultView : RelativeLayout
    lateinit var operationButtonView : LinearLayout
    lateinit var resultTextView : TextView
    lateinit var resetButton : Button

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt("viewMode",ActivityA.VIEW_MODE)
        outState?.putString("result",ActivityA.RESULT_STRING)
        Log.i("ON SAVE INSTOACE","on save instance state")
        super.onSaveInstanceState(outState)

        //outState?.putString("result",)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("ON RESTORE","on restore nistance state")
        val result = savedInstanceState?.getInt("viewMode")
        resultTextView.text = savedInstanceState?.getString("result")
        //println(result)
        setViewVisiblity(result!!)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        if(savedInstanceState == null){
            Log.w("warn","bundel is null")
            ActivityA.VIEW_MODE = 0
        }

        resultView = findViewById<RelativeLayout>(R.id.result_view)
        operationButtonView = findViewById<LinearLayout>(R.id.operation_btn_view)
        val addButton = findViewById<Button>(R.id.add_operation_button)
        val subButton = findViewById<Button>(R.id.sub_operation_button)
        val mulButton = findViewById<Button>(R.id.mul_operation_button)
        val divButton = findViewById<Button>(R.id.division_operation_button)
        resetButton = findViewById<Button>(R.id.btn_reset)
        //resetButton.visibility = View.INVISIBLE
        setViewVisiblity(ActivityA.VIEW_MODE)
        resultTextView = findViewById(R.id.result_text_view)


        //resultView.visibility = View.INVISIBLE

        addButton.setOnClickListener { callActivityWith(OperationType.ADD) }
        subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
        mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
        divButton.setOnClickListener { callActivityWith(OperationType.DIVISION) }
        resetButton.setOnClickListener {
            ActivityA.VIEW_MODE = 0
            setViewVisiblity(ActivityA.VIEW_MODE)
        }
    }
    private fun callActivityWith(operationType : OperationType){
        val intent : Intent = Intent(this,ActivityB::class.java)
        intent.putExtra("operation",operationType.ordinal)
        //startActivity(intent)
        startActivityForResult(intent,REQUEST_CODE_FOR_ACTIVTY_B)

    }
    private fun setViewVisiblity(viewMode : Int){
        when(viewMode){
            0->{
                operationButtonView.visibility = View.VISIBLE
                resetButton.visibility = View.INVISIBLE
                resultView.visibility = View.INVISIBLE
            }
            1->{
                operationButtonView.visibility = View.INVISIBLE
                resetButton.visibility = View.VISIBLE
                resultView.visibility = View.VISIBLE
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_FOR_ACTIVTY_B){
            if(resultCode == RESULT_OK && data != null){
                operationButtonView.visibility = View.INVISIBLE
                ActivityA.VIEW_MODE = 1
               // Toast.makeText(this,"contxt back to act 1", Toast.LENGTH_SHORT).show()
                var value1 = data?.getDoubleExtra("value1",0.0)
                var value2 = data?.getDoubleExtra("value2",0.0)
                var answer = data?.getDoubleExtra("answer",0.0)
                var type = OperationType.values().get(data?.getIntExtra("operation-type",-1))
                val resultText = "Action  : "+type.toString()+"\nInput 1 : "+value1+"\nInput 2 : "+value2+"\nResult  : "+answer
                ActivityA.RESULT_STRING = resultText

                resultTextView.text = ActivityA.RESULT_STRING
               // resetButton.visibility = View.VISIBLE
                //resultView.visibility = View.VISIBLE
                setViewVisiblity(ActivityA.VIEW_MODE)

            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.i("Config","config changes")



    }

    override fun onDestroy() {
        super.onDestroy()
        //ActivityA.VIEW_MODE=0
        Log.i("Destroy","destro called")
    }
}
