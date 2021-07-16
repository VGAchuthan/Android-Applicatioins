package com.example.user.calculatorapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
//import android.app.Fragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

/*class OperationButtonsFragment : Fragment(R.layout.fragment_operation_buttons){}*/

class OperationButtonsFragment() : Fragment() {
    lateinit private var resultView : LinearLayout
    lateinit private var operationButtonView : View
    lateinit private var resultTextView : TextView
    lateinit private var resetButton : Button
    //constructor() : super()
    private var view_mode =0
    private var result_string = ""

    override fun onSaveInstanceState(outState: Bundle?) {
        //outState?.putInt("viewMode",ActivityA.VIEW_MODE)
        //outState?.putString("result",ActivityA.RESULT_STRING)
       // outState?.putInt("viewMode",view_mode)
        //outState?.putString("result",result_string)

       // Log.i("ON SAVE INSTOACE  ","on save instance state OPERATION BUTTONS")
        //println(result_string)
        super.onSaveInstanceState(outState)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //Log.e("OnFragCreate","on create in fragment OPERATION BUTTONS")
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
        result_string = getResultFromActivity()
        if(!result_string.isEmpty()){
           // Log.e("TAG","result string is null")
            this.view_mode = 1
        }
        else
            this.view_mode = 0
        setViewVisiblity(view_mode)
        addButton.setOnClickListener { callActivityWith(OperationType.ADD) }
        subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
        mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
        divButton.setOnClickListener { callActivityWith(OperationType.DIVISION) }
        resetButton.setOnClickListener {

            this.view_mode = 0
            setViewVisiblity(view_mode)
            (activity as ActivityA).setResultString("")
            //onDestroyView()
            //setViewVisiblity(ActivityA.VIEW_MODE)
        }
    }
    private fun callActivityWith(operationType: OperationType){
        //this.view_mode = 1
        //setViewVisiblity(view_mode)
        val ordinalValue = OperationType.valueOf(operationType.toString())
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
                resultTextView.text = result_string
            }
        }
    }
    fun getResultFromActivity() : String{
        result_string = (activity as ActivityA).getResultString()
        return result_string
    }
    fun setViewMode(view_mode : Int){
        this.view_mode = view_mode
       // setViewVisiblity(this.view_mode)
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
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OperationButtonsFragment.
         */
        // TODO: Rename and change types and number of parameters
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
