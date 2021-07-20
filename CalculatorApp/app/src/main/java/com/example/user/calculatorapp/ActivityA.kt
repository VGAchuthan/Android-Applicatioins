package com.example.user.calculatorapp


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_a.*

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
        //var sharedPreference : SharedPreferences = getSharedPreferences("Operations", Context.MODE_PRIVATE)
        //val editor = sharedPreference.edit()
        //editor.p
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"PORTRAIT ODE", Toast.LENGTH_SHORT).show()
            //setContentView(R.layout.activity_a)
            setViewVisiblity()
        }
        else{
            // setViewVisiblity()
            setLayoutForLandscape()
        }


//        if(!operationButtonFragment.isAdded){
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.left_fragment, operationButtonFragment)
//                commit()
//            }
//        }
//        if(!calculationFragment.isAdded){
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.right_fragment, calculationFragment)
//                commit()
//            }
//        }



    }

    override fun onResume() {

        super.onResume()

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


                right_fragment.visibility = View.INVISIBLE
            }
            1 -> {
                left_fragment.visibility = View.VISIBLE
                right_fragment.visibility = View.VISIBLE
                if(calculationFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        remove( calculationFragment)
                        commit()
                    }
                }
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.right_fragment, calculationFragment)
                    commit()
                }

            }
            2->{
                println("in landscape mode 2 activity")
                right_fragment.visibility = View.INVISIBLE

//                if(operationButtonFragment.isAdded){
//                    supportFragmentManager.beginTransaction().apply {
//
//                        commit()
//                    }
//
//                }
                //if(operationButtonFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        remove( operationButtonFragment)
                        //commit()

                        commitNow()
                    }
                supportFragmentManager.beginTransaction().apply {

                    //commit()
                    add(R.id.left_fragment, operationButtonFragment)
                    commitNow()
                }
               // }
                left_fragment.visibility = View.VISIBLE


            }
        }
    }


    private fun setViewVisiblity(){
        when(view_mode){
            0,2 ->{
                Toast.makeText(this,"mode is 0", Toast.LENGTH_SHORT).show()
                println("Result String")
                println(result_string)
                left_fragment.visibility = View.VISIBLE
                if(!operationButtonFragment.isAdded){
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.left_fragment, operationButtonFragment)
                    commitNow()
                }

            }
                supportFragmentManager.beginTransaction().apply {
                    remove(calculationFragment)
                    commit()
                }

                right_fragment.visibility = View.INVISIBLE

            }
            1 ->{
                Toast.makeText(this,"mode is 1", Toast.LENGTH_SHORT).show()
                right_fragment.visibility = View.VISIBLE
                if(!calculationFragment.isAdded){
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.right_fragment, calculationFragment)
                        commit()
                    }
                }
                supportFragmentManager.beginTransaction().apply {
                    remove(operationButtonFragment)
                    commit()
                }
                left_fragment.visibility = View.INVISIBLE
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

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"PORTRAIT ODE", Toast.LENGTH_SHORT).show()
            //setContentView(R.layout.activity_a)
            setViewVisiblity()
        }
        else{
            // setViewVisiblity()
            setLayoutForLandscape()
        }
        //landscapeViewVisiblity()

    }
    fun getOperationType() : Int{
        return this.operationType
    }
    fun getResultString() :String{
        return this.result_string
    }
    fun setResultString(result_string : String){
        this.result_string = result_string
        this.view_mode = 2
        //OperationsInfo.mode = 2
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this,"PORTRAIT ODE", Toast.LENGTH_SHORT).show()
            //setContentView(R.layout.activity_a)
            setViewVisiblity()
        }
        else{
            // setViewVisiblity()
            setLayoutForLandscape()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        OperationsInfo.mode = 0
//        OperationsInfo.result_string = ""
//        OperationsInfo.operationType = -1

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
