package com.example.lottery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lottery.R

class LottoMaxFragment : Fragment() {

    private lateinit var lottoMaxViewModel: LottoMaxViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lottoMaxViewModel =
            ViewModelProviders.of(this).get(LottoMaxViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lottomax, container, false)
        val textView: TextView = root.findViewById(R.id.text_lottomax)
        lottoMaxViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}