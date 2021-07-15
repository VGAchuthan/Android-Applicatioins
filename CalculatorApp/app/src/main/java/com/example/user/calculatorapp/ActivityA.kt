package com.example.user.calculatorapp

import android.app.Activity

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Fragment
//import android.support.v4.app.Fragment
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

    lateinit private var resultView : LinearLayout
    lateinit private var operationButtonView : LinearLayout
    lateinit private var resultTextView : TextView
    lateinit private var resetButton : Button
    //0 -> For Operation button view
    // 1 -> Input View

    private var view_mode: Int = 0
    private var result_string : String =""
    private var operationType = 0
    val operationButtonFragment  = OperationButtonsFragment.newInstance()
    val calculationFragment   = CalculationFragment.newInstance()



    override fun onSaveInstanceState(outState: Bundle?) {
        //outState?.putInt("viewMode",ActivityA.VIEW_MODE)
        //outState?.putString("result",ActivityA.RESULT_STRING)
        outState?.putInt("viewMode",view_mode)
        outState?.putString("result",result_string)
        outState?.putInt("operationType", operationType)

        //Log.i("ON SAVE INSTOACE","on save instance state")
        //println(result_string)
        super.onSaveInstanceState(outState)

        //outState?.putString("result",)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
       // Log.i("ON RESTORE","on restore instance state")
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

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.calculator_fragment, operationButtonFragment)
//            commit()

        //}
        if(savedInstanceState == null){
            view_mode =0
            result_string =""
            operationType = 0
        }
        else{
            view_mode = savedInstanceState.getInt("viewMode")
            result_string = savedInstanceState.getString("result")
            operationType = savedInstanceState.getInt("operationType")
        }
        setViewVisiblity()


    }

    private fun setViewVisiblity(){
        when(view_mode){
            0,2 ->{

                supportFragmentManager.beginTransaction().apply {

                    replace(R.id.calculator_fragment, operationButtonFragment)
                    //addToBackStack(null)
                    commit()


                }
            }
            1 ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.calculator_fragment, calculationFragment)
                    //addToBackStack(null)

                    commit()

                }
            }
        }
    }




    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        //Log.i("Config","config changes")



    }

    override fun onDestroy() {
        super.onDestroy()
        //ActivityA.VIEW_MODE=0
       // Log.i("Destroy","destroy called")
    }
    fun getViewMode() : Int{
        return this.view_mode
    }
    fun setViewMode(viewMode : Int){
        this.view_mode = viewMode
    }
    fun setOperationType( operationType : Int){
        this.operationType = operationType
        this.view_mode = 1
       // val fragment = fragmentManager.findFragmentByTag("OperationButtonsFragment") as OperationButtonsFragment//fragmentManager.findFragmentById(R.id.calculator_fragment) as OperationButtonsFragment
        //fragment
        //fragment.setViewMode(view_mode)
        setViewVisiblity()

    }
    fun getOperationType() : Int{
        return this.operationType
    }
    fun getResultString() :String{
        return this.result_string
    }
    fun setResultString(result_string : String){
        this.result_string = result_string
        this.view_mode=2
        setViewVisiblity()
    }

    override fun onBackPressed() {
//        println("view mode in on babck press")
  //      println(this.view_mode)

        if(this.view_mode == 1){
            this.view_mode = 0
            setViewVisiblity()}
        else
            super.onBackPressed()


    //    Log.e("IN activity","on back pressed")

    }

}
