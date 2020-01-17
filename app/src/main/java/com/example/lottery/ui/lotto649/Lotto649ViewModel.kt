package com.example.lottery.ui.lotto649

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Lotto649ViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Enter Your Lotto 6/49 Number \n( 6 numbers, 1 ~ 49 )"
    }
    val text: LiveData<String> = _text
}