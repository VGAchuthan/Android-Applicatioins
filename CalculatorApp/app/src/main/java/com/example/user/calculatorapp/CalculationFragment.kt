package com.example.user.calculatorapp


import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.user.calculatorapp.enums.OperationType


class CalculationFragment : Fragment() {
    lateinit var value1 : EditText
    lateinit var value2 : EditText
    private var rootView : View? = null
    lateinit private var btn_perform : Button
    private var operationTypeOrdinal : Int = 0


    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //NOTE : receives result from OperationButtonsFragment
        setFragmentResultListener("mode"){key, bundle ->
            operationTypeOrdinal = bundle.getInt("operationType")
            initializeViews(rootView)

        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        rootView =inflater?.inflate(R.layout.fragment_calculation, container, false)
         return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initializeViews(rootView)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    private fun initializeViews(view : View?){
        value1 = view!!.findViewById(R.id.edittext_value1)
        value2 = view!!.findViewById(R.id.edittext_value2)
        resetEdittextViews()
        value1.requestFocus()
        val operationType = OperationType.values().get(operationTypeOrdinal)
        btn_perform = view!!.findViewById<Button>(R.id.btn_perform)

        btn_perform.text=  OperationType.values().get(operationTypeOrdinal).toString()
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
            OperationType.DIVIDE ->{
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
        sendResultToOperationFragment(resultText)

        //NOTE : send result to Activity
        setFragmentResult("showResultView", bundleOf("view_mode" to ViewModes.VIEW_RESULT))

    }
    private fun sendResultToOperationFragment(resultText : String){
        //NOTE : send result to OperationButtonsFragment
        setFragmentResult("result", bundleOf("result_string" to resultText,"view_mode" to ViewModes.VIEW_RESULT))

    }


    private fun resetEdittextViews(){
        value1.text.clear()
        value2.text.clear()
    }


    // TODO: Rename method, update argument and hook method into UI event


    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        // TODO: Rename and change types and number of parameters
        var fragment : CalculationFragment?= null
        fun newInstance(): CalculationFragment {

            if(fragment == null){
                fragment = CalculationFragment()

            }
            return fragment!!
        }
    }
}// Required empty public constructor
