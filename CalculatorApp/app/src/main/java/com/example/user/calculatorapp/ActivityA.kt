package com.example.user.calculatorapp


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class ActivityA : AppCompatActivity() {


    private var view_mode: Int = 0
    //0 -> For Operation button view and result view
    // 1 -> Input View

    private var result_string : String =""
    private var operationType = 0
    private val operationButtonFragment  = OperationButtonsFragment.newInstance()
    private val calculationFragment   = CalculationFragment.newInstance()



    override fun onSaveInstanceState(outState: Bundle?) {

        outState?.putInt("viewMode",view_mode)
        outState?.putString("result",result_string)
        outState?.putInt("operationType", operationType)

        //Log.i("ON SAVE INSTOACE","on save instance state")
        //println(result_string)
        super.onSaveInstanceState(outState)

        //outState?.putString("result",)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

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
            0 ->{
                if(!operationButtonFragment.isAdded){

                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.calculator_fragment, operationButtonFragment)
                        //addToBackStack(null)
                        commit()
                    }
                }

            }
            1 ->{
                if(!calculationFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.calculator_fragment, calculationFragment)
                       // addToBackStack(null)
                        commit()
                    }
                }

            }
        }
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
        this.view_mode=0
        setViewVisiblity()
    }

    override fun onBackPressed() {
//        println("view mode in on babck press")
  //      println(this.view_mode)

        if(this.view_mode == 1){
            this.view_mode = 0
            setViewVisiblity()
        }
        else
            super.onBackPressed()


    //    Log.e("IN activity","on back pressed")

    }

}
