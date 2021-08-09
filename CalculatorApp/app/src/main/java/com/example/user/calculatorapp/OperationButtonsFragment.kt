package com.example.user.calculatorapp

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.user.calculatorapp.providers.MyHistoryProvider

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

    lateinit private var resetButton : Button
    lateinit var recyclerView  : RecyclerView
    private var view_mode  : String = ViewModes.VIEW_OPERATION_BUTTON
    private var result_string = ""
    private var operationResult : OperationResult? = null
    lateinit var dataSet : Array<Views>
    lateinit var adapter : OperationButtonFragmentAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
               super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Log.e("FRAG 1", "ON CREATE VIEW")
        //NOTE : receive results from Calculation Fragment
        setFragmentResultListener("result"){key, bundle ->
            //result_string = bundle.getString("result_string")
            var input1 = bundle.getString("input1")
            var input2 = bundle.getString("input2")
            var answer = bundle.getString("answer")
            var action = bundle.getString("action")
            operationResult = OperationResult(action,input1, input2, answer)
            addToContentProvider(OperationResult(action,input1, input2, answer))

            this.view_mode = ViewModes.VIEW_RESULT
            //    Log.e("FRAGMENT 1","modev: $result_string")
            setViewVisiblity(this.view_mode)

        }

        return inflater?.inflate(R.layout.fragment_operations_button, container, false)
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {

        super.onViewStateRestored(savedInstanceState)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview1)

        //setAdapter()
        dataSet = arrayOf(Views(0),Views(1, true))
        adapter = OperationButtonFragmentAdapter(this, dataSet)
        //adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
        resetButton = view.findViewById<Button>(R.id.btn_reset) as Button
        resetButton.visibility = View.INVISIBLE

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        setViewVisiblity(view_mode)

        // recyclerViewIsAdded= true
            resetButton.setOnClickListener {

                this.view_mode = ViewModes.VIEW_OPERATION_BUTTON
                setViewVisiblity(view_mode)
                this.result_string = ""
                this.operationResult = null
                //NOTE :send result to Activity
                setFragmentResult("resetView", bundleOf("view_mode" to ViewModes.VIEW_OPERATION_BUTTON))
            }
            //resetButton.visibility = View.INVISIBLE
    }

    private fun setViewVisiblity(viewMode : String){
        when(viewMode){
            ViewModes.VIEW_OPERATION_BUTTON->{
                dataSet[1].isHide = true
                dataSet[1].operationResult = null
                operationResult = null
                dataSet[0].isHide = false
                adapter.notifyDataSetChanged()
                resetButton.visibility = View.INVISIBLE
            }
            ViewModes.VIEW_RESULT ->{
                dataSet[1].operationResult = operationResult
                dataSet[1].isHide = false
                dataSet[0].isHide = true
                adapter.notifyDataSetChanged()

                resetButton.visibility = View.VISIBLE
            }
        }

    }

    private fun addToContentProvider(operationResult : OperationResult){
        val values = ContentValues()
        values.put("action", operationResult.action)
        values.put("input1",operationResult.input1)
        values.put("input2",operationResult.input2)
        values.put("result",operationResult.answer)
//        for(value in 1..100)
        activity?.contentResolver?.insert(MyHistoryProvider.CONTENT_URI,values)
//        Toast.makeText(activity,"ADDED IN CONTENT PROVIDER",Toast.LENGTH_SHORT).show()

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

        //var fragment  : OperationButtonsFragment? =null
        fun newInstance(): OperationButtonsFragment {
            var fragment  =  OperationButtonsFragment()
//            if(fragment == null)
//                fragment = OperationButtonsFragment()

            return fragment
        }
    }

}// Required empty public constructor*/
