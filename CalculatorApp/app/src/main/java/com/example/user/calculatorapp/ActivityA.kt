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
    /*companion object {
        //0 -> For Operation button view
        // 1 -> For Result View
        var VIEW_MODE : Int = 0
        var RESULT_STRING : String = ""
    }*/
    private val REQUEST_CODE_FOR_ACTIVTY_B = 1

    lateinit private var resultView : GridLayout
    lateinit private var operationButtonView : GridLayout
    lateinit private var resultTextView : TextView
    lateinit private var resetButton : Button
    //0 -> For Operation button view
    // 1 -> For Result View
    private var view_mode: Int = 0
    private var result_string : String =""

    override fun onSaveInstanceState(outState: Bundle?) {
        //outState?.putInt("viewMode",ActivityA.VIEW_MODE)
        //outState?.putString("result",ActivityA.RESULT_STRING)
        outState?.putInt("viewMode",view_mode)
        outState?.putString("result",result_string)

        Log.i("ON SAVE INSTOACE","on save instance state")
        //println(result_string)
        super.onSaveInstanceState(outState)

        //outState?.putString("result",)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("ON RESTORE","on restore instance state")
        /*val viewMode = savedInstanceState?.getInt("viewMode")
        resultTextView.text = savedInstanceState?.getString("result")
        println(viewMode)
        println("result")
        println(savedInstanceState?.getString("result"))
        setViewVisiblity(viewMode!!)*/

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        resultView = findViewById<GridLayout>(R.id.result_view)
        operationButtonView = findViewById<GridLayout>(R.id.operation_btn_view)
        val addButton = findViewById<Button>(R.id.add_operation_button)
        val subButton = findViewById<Button>(R.id.sub_operation_button)
        val mulButton = findViewById<Button>(R.id.mul_operation_button)
        val divButton = findViewById<Button>(R.id.division_operation_button)
        resetButton = findViewById<Button>(R.id.btn_reset)
        //setViewVisiblity(ActivityA.VIEW_MODE)
        resultTextView = findViewById(R.id.result_text_view)
        if(savedInstanceState == null){
            Log.w("warn","bundel is null")
            // ActivityA.VIEW_MODE = 0
            view_mode = 0


        }
        else{
            Log.w("WARN","bundle is not null")
            println(savedInstanceState?.getString("result"))
            result_string = savedInstanceState?.getString("result")
            resultTextView.text =result_string// savedInstanceState?.getString("result")
            view_mode = savedInstanceState?.getInt("viewMode")!!.toInt()
            //ActivityA.VIEW_MODE = savedInstanceState?.getInt("viewMode")!!.toInt()
        }
        setViewVisiblity(view_mode)
        //setViewVisiblity(ActivityA.VIEW_MODE)


        //resultView.visibility = View.INVISIBLE

        addButton.setOnClickListener { callActivityWith(OperationType.ADD) }
        subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
        mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
        divButton.setOnClickListener { callActivityWith(OperationType.DIVISION) }
        resetButton.setOnClickListener {
           //ActivityA.VIEW_MODE = 0
            view_mode=0
           setViewVisiblity(view_mode)
           //setViewVisiblity(ActivityA.VIEW_MODE)
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
               // operationButtonView.visibility = View.INVISIBLE
               // ActivityA.VIEW_MODE = 1
                view_mode = 1
               // Toast.makeText(this,"contxt back to act 1", Toast.LENGTH_SHORT).show()
                var value1 = data?.getDoubleExtra("value1",0.0)
                var value2 = data?.getDoubleExtra("value2",0.0)
                var answer = data?.getDoubleExtra("answer",0.0)
                var type = OperationType.values().get(data?.getIntExtra("operation-type",-1))
                val resultText = "Action  : "+type.toString()+"\nInput 1 : "+value1+"\nInput 2 : "+value2+"\nResult  : "+answer
                //ActivityA.RESULT_STRING = resultText
                result_string = resultText

                resultTextView.text = result_string
                //resultTextView.text = ActivityA.RESULT_STRING
               // resetButton.visibility = View.VISIBLE
                //resultView.visibility = View.VISIBLE
               // setViewVisiblity(ActivityA.VIEW_MODE)
                setViewVisiblity(view_mode)

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
        Log.i("Destroy","destroy called")
    }
}
