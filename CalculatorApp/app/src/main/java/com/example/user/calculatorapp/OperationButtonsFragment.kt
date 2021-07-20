package com.example.user.calculatorapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.user.calculatorapp.enums.OperationType
import kotlinx.android.synthetic.main.fragment_operation_buttons.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OperationButtonsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OperationButtonsFragment.newInstance] factory method to
 * create an instance of this fragment.
 * class OperationButtonsFragment : Fragment()
 */



class OperationButtonsFragment() : Fragment() {
    lateinit private var resultView : LinearLayout
    lateinit private var operationButtonView : View
    lateinit private var resultTextView : TextView
    lateinit private var resetButton : Button
    private var view_mode =0
    private var result_string = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       // Log.e("OnFragCreate","on create view in fragment")
        return inflater?.inflate(R.layout.fragment_operation_buttons, container, false)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        //Log.e("OnFragCreate","on view state restore in fragment OPERATION BUTTONS")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        resultView = (result_view) as LinearLayout
        operationButtonView = (operation_btn_view)
        val addButton = (add_operation_button) as Button
        val subButton = (sub_operation_button) as Button
        val mulButton = (mul_operation_button)as Button
        val divButton = (division_operation_button)as Button
        resetButton = (btn_reset) as Button
        resultTextView = (result_text_view) as  TextView
        result_string = OperationsInfo.result_string
        println("after press add\n result string in operation fragmnent")
        println(result_string)
        if(OperationsInfo.mode == 2 && !result_string.isEmpty() ){
            this.view_mode = 1
        }
        else
            this.view_mode = 0
        setViewVisiblity(view_mode)
        addButton.setOnClickListener {
            callActivityWith(OperationType.ADD) }
        subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
        mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
        divButton.setOnClickListener { callActivityWith(OperationType.DIVISION) }
        resetButton.setOnClickListener {

            this.view_mode = 0
            setViewVisiblity(view_mode)
            this.result_string =""
            OperationsInfo.mode = 0
            OperationsInfo.result_string =""
            OperationsInfo.operationType = -1
            (activity as ActivityA).setResultString("")

        }
    }
    private fun callActivityWith(operationType: OperationType){
        val ordinalValue = OperationType.valueOf(operationType.toString())
        OperationsInfo.operationType = ordinalValue.ordinal
        (activity as ActivityA).setOperationType(ordinalValue.ordinal)
    }
    private fun setViewVisiblity(viewMode : Int){
        when(viewMode){
            0->{

                operationButtonView.visibility = View.VISIBLE
                resetButton.visibility = View.INVISIBLE
                resultView.visibility = View.INVISIBLE
            }
            1->{

                operationButtonView.visibility = View.INVISIBLE
                resetButton.visibility = View.VISIBLE
                resultView.visibility = View.VISIBLE
                resultTextView.text = OperationsInfo.result_string
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */


    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

        var fragment  : OperationButtonsFragment? = null
        fun newInstance(): OperationButtonsFragment {

            val args = Bundle()
            //Log.e("INSTNCE CREATE","INSTACE FOR OPEraton fragment")
            var localfragment = fragment
            if(fragment == null)
                fragment = OperationButtonsFragment()

            return fragment!!
        }
    }

}// Required empty public constructor*/
