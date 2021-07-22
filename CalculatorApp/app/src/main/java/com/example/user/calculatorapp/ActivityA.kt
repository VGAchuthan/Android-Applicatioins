package com.example.user.calculatorapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResultListener


class ActivityA : FragmentActivity() {


    private var view_mode: String = ViewModes.VIEW_OPERATION_BUTTON


    private var result_string : String =""
    private var operationType = 0
    private val operationButtonFragment  = OperationButtonsFragment.newInstance()
    private val calculationFragment   = CalculationFragment.newInstance()

    private var orientation : Int = -1
    lateinit var left_fragment : View
    lateinit var right_fragment : View


    override fun onSaveInstanceState(outState: Bundle) {

        outState?.putString("viewMode",view_mode)
        outState?.putString("result",result_string)
        outState?.putInt("operationType", operationType)
        super.onSaveInstanceState(outState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        //NOTE : receives result from OperationButtonsFragment
        supportFragmentManager.setFragmentResultListener("showCalculationFragment", this){ requestKey, bundle ->
            this.view_mode = bundle.getString("view_mode")
            //Log.e("IN MaiN LISTENER","SHOW CALC FRAGMENT VIEW MODE : $view_mode")
            viewVisiblity()
        }
        //NOTE : receives result from CalculationFragment
        supportFragmentManager.setFragmentResultListener("showResultView", this){ requestKey, bundle ->
            this.view_mode = bundle.getString("view_mode")
           // Log.e("IN MaiN LISTENER","SHOW Result VIEW MODE : $view_mode")
            viewVisiblity()
        }
        //NOTE : receives result from OperationButtonsFragment
        supportFragmentManager.setFragmentResultListener("resetView", this){ requestKey, bundle ->
           this.view_mode = bundle.getString("view_mode")
            //Log.e("IN MaiN LISTENER","Reset VIEW MODE : $view_mode")
            viewVisiblity()
        }


        left_fragment = findViewById(R.id.left_fragment)


        orientation = resources.configuration.orientation
        if(savedInstanceState == null){
            view_mode =ViewModes.VIEW_OPERATION_BUTTON
            result_string =""
            operationType = 0
        }
        else{
            view_mode = savedInstanceState.getString("viewMode")
            result_string = savedInstanceState.getString("result")
            operationType = savedInstanceState.getInt("operationType")

        }

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            setFragmentsForPortraitMode()
            viewVisiblity()

        }
        else{
            right_fragment = findViewById(R.id.right_fragment)
            setFragmentsForLandscapeMode()
            viewVisiblity()
        }
    }

    private fun setFragmentsForPortraitMode(){
        if(calculationFragment.isAdded){
            supportFragmentManager.beginTransaction().remove(calculationFragment).commitNow()
        }
        if(!operationButtonFragment.isAdded)
            supportFragmentManager.beginTransaction().add(R.id.left_fragment, operationButtonFragment).commitNow()

    }
    private fun setFragmentsForLandscapeMode(){
        if(!operationButtonFragment.isAdded)
            supportFragmentManager.beginTransaction().add(R.id.left_fragment, operationButtonFragment).commitNow()
        else{
            //supportFragmentManager.beginTransaction().show( operationButtonFragment).commitNow()
            supportFragmentManager.beginTransaction().remove( operationButtonFragment).commitNow()
            supportFragmentManager.beginTransaction().add(R.id.left_fragment, operationButtonFragment).commitNow()
        }
        if(calculationFragment.isAdded){
            supportFragmentManager.beginTransaction().remove(calculationFragment).commitNow()
        }
        supportFragmentManager.beginTransaction().add(R.id.right_fragment, calculationFragment).commitNow()

    }
    private fun viewVisiblity(){
        if(orientation == Configuration.ORIENTATION_PORTRAIT){

            portraitViewVisiblity()
        }
        else{
            landscapeViewVisiblity()
        }

    }


    private fun landscapeViewVisiblity(){
        when(view_mode){
            ViewModes.VIEW_OPERATION_BUTTON -> {
                supportFragmentManager.beginTransaction().show(operationButtonFragment).commitNow()
                supportFragmentManager.beginTransaction().hide(calculationFragment).commitNow()
                left_fragment.visibility = View.VISIBLE

                right_fragment.visibility = View.INVISIBLE
            }
            ViewModes.VIEW_CALCULATION -> {
                supportFragmentManager.beginTransaction().show(calculationFragment).commitNow()
                left_fragment.visibility = View.VISIBLE
                right_fragment.visibility = View.VISIBLE

            }
            ViewModes.VIEW_RESULT->{

                right_fragment.visibility = View.INVISIBLE
                supportFragmentManager.beginTransaction().hide(calculationFragment).commitNow()

                supportFragmentManager.beginTransaction().show(operationButtonFragment).commitNow()

                left_fragment.visibility = View.VISIBLE


            }
        }
    }


    private fun portraitViewVisiblity(){
        when(view_mode){
            ViewModes.VIEW_OPERATION_BUTTON, ViewModes.VIEW_RESULT ->{
                left_fragment.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction().replace(R.id.left_fragment,operationButtonFragment).commitNow()

            }
            ViewModes.VIEW_CALCULATION ->{
                supportFragmentManager.beginTransaction().replace(R.id.left_fragment,CalculationFragment.newInstance()).commitNow()

                left_fragment.visibility = View.VISIBLE
            }

        }
    }


    override fun onBackPressed() {
        if(this.view_mode == ViewModes.VIEW_CALCULATION){
            this.view_mode = ViewModes.VIEW_OPERATION_BUTTON
            viewVisiblity()
        }
        else if(this.view_mode == ViewModes.VIEW_RESULT){
            this.view_mode = ViewModes.VIEW_OPERATION_BUTTON
            viewVisiblity()
            super.onBackPressed()
        }
        else
            super.onBackPressed()
    }
}
