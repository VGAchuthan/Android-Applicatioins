package com.example.user.calculatorapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.user.calculatorapp.enums.OperationType

class ActivityB : AppCompatActivity() {
    lateinit var performButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        val intent : Intent = intent
        //val operationType = intent.getExtra("operation")
        val operationType = OperationType.values().get(intent.getIntExtra("operation",-1))
        val value1 = findViewById<EditText>(R.id.edittext_value1)
        val value2 = findViewById<EditText>(R.id.edittext_value2)
        performButton= findViewById(R.id.btn_perform)

        //Toast.makeText(this,"Operation is $operationType",Toast.LENGTH_LONG).show()
        performButton.text = operationType.toString()
        performButton.setOnClickListener {
            if((value1.text.toString().isEmpty()) && (value2.text.toString().isEmpty())){
                Toast.makeText(this,"Enter all numbers", Toast.LENGTH_SHORT).show()
            }
            else if(value1.text.toString().isEmpty()){
                value1.requestFocus()
                Toast.makeText(this,"Enter Value 1", Toast.LENGTH_SHORT).show()
            }
            else if(value2.text.toString().isEmpty()){
                value2.requestFocus()
                Toast.makeText(this,"Enter Value 2", Toast.LENGTH_SHORT).show()
            }
            else
                performOperation(value1.text.toString().toDouble(),value2.text.toString().toDouble(), operationType) }
    }
    private fun performOperation(value1 : Double, value2 : Double, type : OperationType){
        when(type){
            OperationType.ADD ->{
                returnValuesBackToCallingActivity(value1,value2,value1+value2,type)

            }
            OperationType.SUB ->{
                returnValuesBackToCallingActivity(value1,value2,value1-value2,type)

            }
            OperationType.MULTIPLY ->{
                returnValuesBackToCallingActivity(value1,value2,value1*value2,type)

            }
            OperationType.DIVISION ->{
                if(value2 == 0.0){
                    Toast.makeText(this,"Enter Non Zero Value for Value 2 ", Toast.LENGTH_SHORT).show()
                }
                else
                    returnValuesBackToCallingActivity(value1,value2,value1/value2,type)

            }
        }
    }
    private fun returnValuesBackToCallingActivity(value1 : Double, value2 : Double,answer : Double, type : OperationType){
        val resultIntent = Intent(this, ActivityA::class.java)
        resultIntent.putExtra("value1",value1)
        resultIntent.putExtra("value2",value2)
        resultIntent.putExtra("answer",answer)
        resultIntent.putExtra("operation-type",type.ordinal)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()


    }
}
