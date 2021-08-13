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
import com.example.user.calculatorapp.roomdatabase.Functions

/**
 * Created by User on 30-07-2021.
 */
class OperationButtonFragmentAdapter(context : OperationButtonsFragment, dataSet: Array<Views> , functionList : List<Functions>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val fragmentContext = context
    private val list = dataSet
    private val _functionList = functionList

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
        init {
            addButton.text = _functionList[0].action
            subButton.text = _functionList[1].action
            mulButton.text = _functionList[2].action
            divButton.text = _functionList[3].action
        }


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
        var action = itemView.findViewById<TextView>(R.id.result_text_view_action)
        var input1 = itemView.findViewById<TextView>(R.id.result_text_view_input1)
        var input2 = itemView.findViewById<TextView>(R.id.result_text_view_input2)
        var result = itemView.findViewById<TextView>(R.id.result_text_view_answer)

        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            if(recyclerViewModel.operationResult != null){
                action.text = "Action  : " + recyclerViewModel.operationResult?.action
                input1.text = "Input 1 : " + recyclerViewModel.operationResult?.input1
                input2.text = "Input 2 : " + recyclerViewModel.operationResult?.input2
                result.text = "Result  : " + recyclerViewModel.operationResult?.answer
            }


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

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        println(holder.adapterPosition)
        super.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return super.onFailedToRecycleView(holder)
    }
    companion object {
        val VIEW_TYPE_BUTTONS = 0//ViewModes.VIEW_OPERATION_BUTTON
        val VIEW_TYPE_RESULT = 1//ViewModes.VIEW_RESULT
    }
}