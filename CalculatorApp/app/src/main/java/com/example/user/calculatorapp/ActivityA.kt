package com.example.user.calculatorapp

import android.content.Context
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.Toast


class ActivityA : AppCompatActivity() {


    private var view_mode: Int = 0
    //0 -> For Operation button view and result view
    // 1 -> Input View

    private var result_string : String =""
    private var operationType = 0
    private val operationButtonFragment  = OperationButtonsFragment.newInstance()
    private val calculationFragment   = CalculationFragment.newInstance()
   lateinit private var dispaly : Display
    private var orientation : Int = -1
    lateinit var left_fragment : View
    lateinit var right_fragment : View


    override fun onSaveInstanceState(outState: Bundle?) {

        outState?.putInt("viewMode",view_mode)
        outState?.putString("result",result_string)
        outState?.putInt("operationType", operationType)
        super.onSaveInstanceState(outState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        left_fragment = findViewById(R.id.left_fragment)

        dispaly = (getSystemService(Context.WINDOW_SERVICE)as WindowManager).defaultDisplay
        orientation = resources.configuration.orientation//dispaly.orientation
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
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"PORTRAIT ODE", Toast.LENGTH_SHORT).show()
            setViewVisiblity()
        }
        else{
            right_fragment = findViewById(R.id.right_fragment)
            // setViewVisiblity()
            setLayoutForLandscape()
        }
    }
    private fun setLayoutForLandscape(){
        Toast.makeText(this,"LANDSCAPE ODE", Toast.LENGTH_SHORT).show()
        landscapeViewVisiblity()
    }

    private fun landscapeViewVisiblity(){
        when(view_mode){
            0 -> {
                left_fragment.visibility = View.VISIBLE
                if(!operationButtonFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.left_fragment, operationButtonFragment)
                        commitNow()
                    }

                }


                right_fragment?.visibility = View.INVISIBLE
            }
            1 -> {
                if(!operationButtonFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.left_fragment, operationButtonFragment)
                        commitNow()
                    }
                }
                if(calculationFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        remove( calculationFragment)
                        commitNow()
                    }
                }
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.right_fragment, calculationFragment)
                    commitNow()
                }
                left_fragment.visibility = View.VISIBLE
                right_fragment.visibility = View.VISIBLE

            }
            2->{
                println("in landscape mode 2 activity")
                right_fragment.visibility = View.INVISIBLE

                supportFragmentManager.beginTransaction().apply {
                        remove( operationButtonFragment)
                        commitNow()
                    }
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.left_fragment, operationButtonFragment)
                    commitNow()
                }

                left_fragment.visibility = View.VISIBLE


            }
        }
    }


    private fun setViewVisiblity(){
        when(view_mode){
            0,2 ->{
                left_fragment.visibility = View.VISIBLE
                if(calculationFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        remove(calculationFragment)
                        commitNow()
                    }
                }
                if(!operationButtonFragment.isAdded){
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.left_fragment, operationButtonFragment)
                    commitNow()
                }

            }
            }
            1 ->{

                if(calculationFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        remove(calculationFragment)
                        commitNow()
                    }

                }
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.left_fragment, calculationFragment)
                    commitNow()
                }
                left_fragment.visibility = View.VISIBLE
            }

        }
    }

    fun setOperationType( operationType : Int){
        this.operationType = operationType
        this.view_mode = 1

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"PORTRAIT ODE", Toast.LENGTH_SHORT).show()
            setViewVisiblity()
        }
        else{
            setLayoutForLandscape()
        }
    }

    fun setResultString(result_string : String){
        this.result_string = result_string
        this.view_mode = 2
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"PORTRAIT ODE", Toast.LENGTH_SHORT).show()
            setViewVisiblity()
        }
        else{
            setLayoutForLandscape()
        }
    }
    override fun onBackPressed() {
        if(this.view_mode == 1){
            this.view_mode = 0
            setViewVisiblity()
        }
        else
            super.onBackPressed()
    }
}
