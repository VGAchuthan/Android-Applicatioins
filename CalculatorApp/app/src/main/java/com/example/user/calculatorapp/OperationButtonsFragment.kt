package com.example.user.calculatorapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.user.calculatorapp.enums.OperationType



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
    private var view_mode  : String = ViewModes.VIEW_OPERATION_BUTTON
    private var result_string = ""

    init{


    }



    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    override fun onAttach(context: Context) {
        //NOTE : receive results from Calculation Fragment
        setFragmentResultListener("result"){key, bundle ->
            result_string = bundle.getString("result_string")

            this.view_mode = ViewModes.VIEW_RESULT
            Log.e("FRAGMENT 1","modev: $result_string")
            setViewVisiblity(this.view_mode)

        }
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_operations_button, container, false)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        resultView = view.findViewById(R.id.result_view)
        operationButtonView = view.findViewById(R.id.operation_btn_view)
        val addButton = view.findViewById<Button>(R.id.add_operation_button)
        val subButton = view.findViewById<Button>(R.id.sub_operation_button) as Button
        val mulButton = view.findViewById<Button>(R.id.mul_operation_button)as Button
        val divButton = view.findViewById<Button>(R.id.division_operation_button)as Button
        resetButton = view.findViewById<Button>(R.id.btn_reset) as Button
        resultTextView = view.findViewById(R.id.result_text_view)
        //
        setViewVisiblity(view_mode)
        addButton.setOnClickListener {
            callActivityWith(OperationType.ADD) }
        subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
        mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
        divButton.setOnClickListener { callActivityWith(OperationType.DIVIDE) }
        resetButton.setOnClickListener {

            this.view_mode = ViewModes.VIEW_OPERATION_BUTTON
            setViewVisiblity(view_mode)
            this.result_string =""
            //NOTE :send result to Activity
            setFragmentResult("resetView", bundleOf("view_mode" to ViewModes.VIEW_OPERATION_BUTTON))


        }
    }
    private fun callActivityWith(operationType: OperationType){

        //NOTE : Send Result to Calculation Fragment
        setFragmentResult("mode", bundleOf("operationType" to operationType.ordinal))
        //NOTE : send Result to Activity
        setFragmentResult("showCalculationFragment", bundleOf("view_mode" to ViewModes.VIEW_CALCULATION))


    }
    private fun setViewVisiblity(viewMode : String){
        when(viewMode){
            ViewModes.VIEW_OPERATION_BUTTON->{

                operationButtonView.visibility = View.VISIBLE
                resetButton.visibility = View.INVISIBLE
                resultView.visibility = View.INVISIBLE
            }
            ViewModes.VIEW_RESULT->{

                operationButtonView.visibility = View.INVISIBLE
                resetButton.visibility = View.VISIBLE
                resultView.visibility = View.VISIBLE
                resultTextView.text = result_string
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

            if(fragment == null)
                fragment = OperationButtonsFragment()

            return fragment!!
        }
    }

}// Required empty public constructor*/
