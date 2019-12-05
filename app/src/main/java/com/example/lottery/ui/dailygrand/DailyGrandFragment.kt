package com.example.lottery.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lottery.R

class DailyGrandFragment : Fragment() {

    private lateinit var dailyGrandViewModel: DailyGrandViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dailyGrandViewModel =
            ViewModelProviders.of(this).get(DailyGrandViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dailygrand, container, false)
        val textView: TextView = root.findViewById(R.id.text_dailygrand)
        dailyGrandViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}