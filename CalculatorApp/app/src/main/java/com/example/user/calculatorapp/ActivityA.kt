package com.example.user.calculatorapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toolbar

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResultListener
import com.example.user.calculatorapp.history.HistoryActivity


class ActivityA : FragmentActivity() {

    private var view_mode: String = ViewModes.VIEW_OPERATION_BUTTON
    private var result_string : String =""
    private var operationType = 0

    private var operationButtonFragment  = OperationButtonsFragment.newInstance()
    private val calculationFragment   = CalculationFragment.newInstance()


    private var orientation : Int = -1
    lateinit var left_fragment : View
    lateinit var right_fragment : View

    override fun onSaveInstanceState(outState: Bundle) {
        Log.e("ACTIVITY LIFECYCLE", "ON SAVE INSTANCE  IS CALLED")

        outState?.putString("viewMode",view_mode)
        outState?.putString("result",result_string)
        outState?.putInt("operationType", operationType)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {


        Log.e("ACTIVITY LIFECYCLE","ON RESTORE CALLED")
        super.onRestoreInstanceState(savedInstanceState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        val actionBar = getActionBar()
        setTitle(R.string.app_name)
        val toolbar = findViewById<Toolbar>(R.id.main_activity_toolbar)
        setActionBar(toolbar)
//        actionBar.setDisplayHomeAsUpEnabled(true)
        //setActionBar()

        //NOTE : receives result from OperationButtonsFragment
        supportFragmentManager.setFragmentResultListener("showCalculationFragment", this){ requestKey, bundle ->
            this.view_mode = bundle.getString("view_mode")
            //Log.e("IN MaiN LISTENER","SHOW CALC FRAGMENT VIEW MODE : $view_mode")
            viewVisiblity()
        }

        //NOTE : receives result from CalculationFragment
        supportFragmentManager.setFragmentResultListener("showResultView", this){ requestKey, bundle ->
            this.view_mode = bundle.getString("view_mode")
            //Log.e("IN MaiN LISTENER","SHOW Result VIEW MODE : $view_mode")
            viewVisiblity()
        }
        //NOTE : receives result from OperationButtonsFragment
        supportFragmentManager.setFragmentResultListener("resetView", this){ requestKey, bundle ->
           this.view_mode = bundle.getString("view_mode")

            //Log.e("IN MaiN LISTENER","Reset VIEW MODE : $view_mode")
//            supportFragmentManager.beginTransaction().hide(operationButtonFragment).commitNow()
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
            operationButtonFragment = supportFragmentManager.findFragmentById(R.id.left_fragment) as OperationButtonsFragment


        }
        Log.e("ACTIVITY LIFECYCLE","ON CREATE")
        println(operationButtonFragment)


    }


    override fun onStart() {
        super.onStart()
        left_fragment = findViewById(R.id.left_fragment)
        orientation = resources.configuration.orientation

        // NOTE : OperationButtonFragment is added to Left Fragment irrespective of orientation mode

        if(!operationButtonFragment.isAdded){
            supportFragmentManager.beginTransaction().apply {
                add(R.id.left_fragment,operationButtonFragment)
//                addToBackStack("Add OperationButtonFragment")
                commitNow()
            }

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

        Log.e("ACTIVITY LIFECYCLE","ON START")
    }

    override fun onResume() {
        super.onResume()

        Log.e("ACTIVITY LIFECYCLE","ON RESUME")
    }
    private fun setFragmentsForPortraitMode(){
        if(!calculationFragment.isAdded)
            supportFragmentManager.beginTransaction().add(R.id.left_fragment,calculationFragment)/*.addToBackStack("Add CalculationFragment")*/.commitNow()
    }
    private fun setFragmentsForLandscapeMode(){
//
        if(!calculationFragment.isAdded)
            supportFragmentManager.beginTransaction().add(R.id.right_fragment, calculationFragment)/*.addToBackStack("Add CalculationFragment")*/.commitNow()

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
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                    show(calculationFragment)
                    commitNow()
                }
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
                supportFragmentManager.beginTransaction().apply {
                    //setCustomAnimations(R.anim.abc_slide_out_bottom, R.anim.abc_slide_out_bottom)
                    hide(calculationFragment)
                    setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                    show(operationButtonFragment)

                    commitNow()
                }
                //println(supportFragmentManager.backStackEntryCount)
            }
            ViewModes.VIEW_CALCULATION ->{
                supportFragmentManager.beginTransaction().apply {
                    //setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                    hide(operationButtonFragment)
                    setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)
                    show(calculationFragment)
                    //setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom)

                    commitNow()
                }
            }

        }
    }

    override fun onPause() {
        Log.e("ACTIVITY LIFECYCLE", "ON PAUSE IS CALLED")
      super.onPause()
    }

    override fun onStop() {


        Log.e("ACTIVITY LIFECYCLE","ON STOP")
        println(operationButtonFragment)
        //if(orientation == Configuration.ORIENTATION_PORTRAIT){
            supportFragmentManager.beginTransaction().remove(calculationFragment)
//                .remove(operationButtonFragment)
                    .commitNow()
        //}
        super.onStop()
    }

    override fun onDestroy() {

        super.onDestroy()
        Log.e("ACTIVITY LIFECYCLE","ON DESTROY")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //val menuInflater =
        menuInflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when(item!!.itemId) {

            R.id.menu_action_history -> {
                val historyIntent = Intent(this,HistoryActivity::class.java)
                startActivity(historyIntent)
                true
            }
        else->{
            super.onOptionsItemSelected(item)
        }

//        return
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
