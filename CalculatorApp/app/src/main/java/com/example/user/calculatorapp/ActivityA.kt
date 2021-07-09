package com.example.user.calculatorapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.user.calculatorapp.enums.OperationType

class ActivityA : AppCompatActivity() {
    private val REQUEST_CODE_FOR_ACTIVTY_B = 1

    lateinit var resultView : RelativeLayout
    lateinit var operationButtonView : LinearLayout
    lateinit var resultTextView : TextView
    lateinit var resetButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        resultView = findViewById<RelativeLayout>(R.id.result_view)
        operationButtonView = findViewById<LinearLayout>(R.id.operation_btn_view)
        val addButton = findViewById<Button>(R.id.add_operation_button)
        val subButton = findViewById<Button>(R.id.sub_operation_button)
        val mulButton = findViewById<Button>(R.id.mul_operation_button)
        val divButton = findViewById<Button>(R.id.division_operation_button)
        resetButton = findViewById<Button>(R.id.btn_reset)
        resetButton.visibility = View.INVISIBLE
        resultTextView = findViewById(R.id.result_text_view)


        resultView.visibility = View.INVISIBLE

        addButton.setOnClickListener { callActivityWith(OperationType.ADD) }
        subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
        mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
        divButton.setOnClickListener { callActivityWith(OperationType.DIVISION) }
        resetButton.setOnClickListener { resetViews() }
    }
    private fun callActivityWith(operationType : OperationType){
        val intent : Intent = Intent(this,ActivityB::class.java)
        intent.putExtra("operation",operationType.ordinal)
        //startActivity(intent)
        startActivityForResult(intent,REQUEST_CODE_FOR_ACTIVTY_B)

    }
    private fun resetViews(){
        resultView.visibility = View.INVISIBLE
        operationButtonView.visibility = View.VISIBLE
        resetButton.visibility = View.INVISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_FOR_ACTIVTY_B){
            if(resultCode == RESULT_OK && data != null){
                operationButtonView.visibility = View.INVISIBLE
               // Toast.makeText(this,"contxt back to act 1", Toast.LENGTH_SHORT).show()
                var value1 = data?.getDoubleExtra("value1",0.0)
                var value2 = data?.getDoubleExtra("value2",0.0)
                var answer = data?.getDoubleExtra("answer",0.0)
                var type = OperationType.values().get(data?.getIntExtra("operation-type",-1))
                val resultText = "Action  : "+type.toString()+"\nInput 1 : "+value1+"\nInput 2 : "+value2+"\nResult  : "+answer

                resultTextView.text = resultText
                resetButton.visibility = View.VISIBLE
                resultView.visibility = View.VISIBLE

            }
        }
    }
}
