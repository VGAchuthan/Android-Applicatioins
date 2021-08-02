package com.example.user.calculatorapp

/**
 * Created by User on 30-07-2021.
 */
//Data class used to store data for the adapter in recycler view
data class Views(val viewType:Int, var isHide : Boolean = false,var resultString : String? = null)