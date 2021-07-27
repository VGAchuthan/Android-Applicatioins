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
            Log.e("IN MaiN LISTENER","SHOW Result VIEW MODE : $view_mode")
            viewVisiblity()
        }
        //NOTE : receives result from OperationButtonsFragment
        supportFragmentManager.setFragmentResultListener("resetView", this){ requestKey, bundle ->
           this.view_mode = bundle.getString("view_mode")
            //Log.e("IN MaiN LISTENER","Reset VIEW MODE : $view_mode")
            viewVisiblity()
        }
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

    }

    override fun onResume() {

        left_fragment = findViewById(R.id.left_fragment)
        orientation = resources.configuration.orientation

        // NOTE : OperationButtonFragment is added to Left Fragment irrespective of orientation mode
        if(!operationButtonFragment.isAdded)
            supportFragmentManager.beginTransaction().add(R.id.left_fragment, operationButtonFragment).commitNow()

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            setFragmentsForPortraitMode()
            viewVisiblity()

        }
        else{
            right_fragment = findViewById(R.id.right_fragment)
            setFragmentsForLandscapeMode()
            viewVisiblity()
        }
        super.onResume()
    }
    private fun setFragmentsForPortraitMode(){
            supportFragmentManager.beginTransaction().add(R.id.left_fragment,calculationFragment).commitNow()
    }
    private fun setFragmentsForLandscapeMode(){
//
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

                right_fragment.visibility = View.INVISIBLE
            }
            ViewModes.VIEW_CALCULATION -> {
                supportFragmentManager.beginTransaction().show(calculationFragment).commitNow()
                /*NOTE : This show transaction is efficient when we rotate device from ViewModes.VIEW_CALCULATION in
                  portrait mode to landscape , because in portrait mode operation button fragment is hided */
                supportFragmentManager.beginTransaction().show(operationButtonFragment).commitNow()
                right_fragment.visibility = View.VISIBLE

            }
            ViewModes.VIEW_RESULT->{
                right_fragment.visibility = View.INVISIBLE
            }
        }
    }


    private fun portraitViewVisiblity(){
        when(view_mode){
            ViewModes.VIEW_OPERATION_BUTTON, ViewModes.VIEW_RESULT ->{
                supportFragmentManager.beginTransaction().show(operationButtonFragment).hide(calculationFragment).commitNow()
                println(supportFragmentManager.backStackEntryCount)
            }
            ViewModes.VIEW_CALCULATION ->{
                supportFragmentManager.beginTransaction().hide(operationButtonFragment).show(calculationFragment).commitNow()
            }

        }
    }

    override fun onPause() {
      supportFragmentManager.beginTransaction().remove(calculationFragment).commitNow()
      super.onPause()
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
