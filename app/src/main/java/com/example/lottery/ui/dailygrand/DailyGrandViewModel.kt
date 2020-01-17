package com.example.lottery.ui.dailygrand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DailyGrandViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Enter Your Daily Grand Number \n( 5 numbers, 1 ~ 49 )"
    }
    val text: LiveData<String> = _text
}