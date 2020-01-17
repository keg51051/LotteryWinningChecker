package com.example.lottery.ui.lottomax

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LottoMaxViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Enter Your Lotto Max Number \n( 7 numbers, 1 ~ 50 )"
    }
    val text: LiveData<String> = _text
}