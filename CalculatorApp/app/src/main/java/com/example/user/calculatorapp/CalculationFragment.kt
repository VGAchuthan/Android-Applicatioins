package com.example.user.calculatorapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
//import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.user.calculatorapp.enums.OperationType
import kotlinx.android.synthetic.main.fragment_calculation.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CalculationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CalculationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculationFragment : Fragment() {
    //private var input1 = 0.0
    //private var input2 = 0.0


    // TODO: Rename and change types of parameters
//    private var mParam1: String? = null
//    private var mParam2: String? = null
//
//    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        //Log.e("OnFragCreate","on create view in fragment CALCULATION")

         return inflater?.inflate(R.layout.fragment_calculation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.e("OnFragCreate","on view created in fragment CALCULATION")
        //println("on calc view created")
        resetEdittextViews()

        val value1 = (edittext_value1) as EditText
        val value2 = (edittext_value2) as EditText
        value1.requestFocus()
        val operationType = OperationType.values().get((activity as ActivityA).getOperationType())
        btn_perform.text= operationType.toString()
        btn_perform.setOnClickListener {


            if((value1.text.toString().isEmpty()) && (value2.text.toString().isEmpty())){
                Toast.makeText(activity,"Enter all numbers", Toast.LENGTH_SHORT).show()
            }
            else if(value1.text.toString().isEmpty()){
                value1.requestFocus()
                Toast.makeText(activity,"Enter Value 1", Toast.LENGTH_SHORT).show()
            }
            else if(value2.text.toString().isEmpty()){
                value2.requestFocus()
                Toast.makeText(activity,"Enter Value 2", Toast.LENGTH_SHORT).show()
            }
            else{
                performOperation(value1.text.toString().toDouble(),value2.text.toString().toDouble(), operationType)

            }
        }
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
                    Toast.makeText(activity,"Enter Non Zero Value for Value 2 ", Toast.LENGTH_SHORT).show()
                }
                else
                    returnValuesBackToCallingActivity(value1,value2,value1/value2,type)

            }
        }
    }
    private fun returnValuesBackToCallingActivity(value1 : Double, value2 : Double,answer : Double, type : OperationType){
        val resultText = "Action  : "+type.toString()+"\nInput 1 : "+value1+"\nInput 2 : "+value2+"\nResult  : "+answer
        resetEdittextViews()
        (activity as ActivityA).setResultString(resultText)
    }


    private fun resetEdittextViews(){
        edittext_value1.text.clear()
        edittext_value2.text.clear()
    }


    // TODO: Rename method, update argument and hook method into UI event


    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculationFragment.
         */
        // TODO: Rename and change types and number of parameters
        var fragment : CalculationFragment?= null
        fun newInstance(): CalculationFragment {

//            val args = Bundle()
//            args.putString(ARG_PARAM1, param1)
//            args.putString(ARG_PARAM2, param2)
//            fragment.arguments = args
        //    Log.e("INSTANCE REATE","FOR CAlculation fragment")
            if(fragment == null){
                fragment = CalculationFragment()

            }
            return fragment!!
        }
    }
}// Required empty public constructor
