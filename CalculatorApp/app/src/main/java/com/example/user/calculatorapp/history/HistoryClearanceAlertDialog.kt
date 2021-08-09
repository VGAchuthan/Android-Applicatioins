package com.example.user.calculatorapp.history

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Created by User on 09-08-2021.
 */
class HistoryClearanceAlertDialog : DialogFragment(){
    companion object {
        const val TAG = "Clearance Alert Dialog"
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                    .setMessage("Clear History ?")
                    .setPositiveButton("Clear") { _,_ -> (activity as HistoryActivity).onAlertPositiveButtonAction()}
                    .setNegativeButton("Dismiss") {_,_ -> dismiss() }
                    .create()
}