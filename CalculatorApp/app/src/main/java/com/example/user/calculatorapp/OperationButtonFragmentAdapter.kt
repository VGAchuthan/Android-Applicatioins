package com.example.user.calculatorapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.example.user.calculatorapp.enums.OperationType

/**
 * Created by User on 30-07-2021.
 */
class OperationButtonFragmentAdapter(context : OperationButtonsFragment, dataSet: Array<Views>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val fragmentContext = context
    private val list = dataSet

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == VIEW_TYPE_BUTTONS){ return OperationButtonsViewHolder(
                LayoutInflater.from(fragmentContext.context).inflate(R.layout.layout_buttons, parent, false)
        )
        }
        return ResultViewHolder(
                LayoutInflater.from(fragmentContext.context).inflate(R.layout.layout_result_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType == VIEW_TYPE_BUTTONS) {
            (holder as OperationButtonsViewHolder).bind(position)
        } else {
            (holder as ResultViewHolder).bind(position)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
    private inner class OperationButtonsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val addButton = itemView.findViewById<Button>(R.id.add_operation_button)
        val subButton = itemView.findViewById<Button>(R.id.sub_operation_button)
        val mulButton = itemView.findViewById<Button>(R.id.mul_operation_button)
        val divButton = itemView.findViewById<Button>(R.id.division_operation_button)
//        val resetButton = itemView.findViewById<Button>(R.id.btn_reset) as Button


        fun bind(position: Int) {
            addButton.setOnClickListener {
                callActivityWith(OperationType.ADD) }
            subButton.setOnClickListener { callActivityWith(OperationType.SUB) }
            mulButton.setOnClickListener { callActivityWith(OperationType.MULTIPLY) }
            divButton.setOnClickListener { callActivityWith(OperationType.DIVIDE) }

            val recyclerViewModel = list[position]
            if(recyclerViewModel.isHide){
                //itemView.visibility = View.INVISIBLE
                itemView.layoutParams.height = 0
            }
            else{
                itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                //itemView.visibility = View.VISIBLE
            }
           // message.text = recyclerViewModel.textData
        }
        private fun callActivityWith(operationType: OperationType){

            //Log.e("IN ADAPTER VIEW 1","${operationType.toString()} is called")

            //NOTE : Send Result to Calculation Fragment
            fragmentContext.setFragmentResult("mode", bundleOf("operationType" to operationType.ordinal))
            //NOTE : send Result to Activity
            fragmentContext.setFragmentResult("showCalculationFragment", bundleOf("view_mode" to ViewModes.VIEW_CALCULATION))


        }

    }

    private inner class ResultViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var resultTextView = itemView.findViewById<TextView>(R.id.result_text_view)
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            resultTextView.text = recyclerViewModel.resultString
            if(recyclerViewModel.isHide){
                //itemView.visibility = View.INVISIBLE
                itemView.layoutParams.height = 0
            }
            else{
                itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                //itemView.visibility = View.VISIBLE
            }

        }

    }
    companion object {
        val VIEW_TYPE_BUTTONS = 0//ViewModes.VIEW_OPERATION_BUTTON
        val VIEW_TYPE_RESULT = 1//ViewModes.VIEW_RESULT
    }
}