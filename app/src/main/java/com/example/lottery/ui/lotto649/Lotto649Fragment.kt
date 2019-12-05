package com.example.lottery.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lottery.R

class Lotto649Fragment : Fragment() {

    private lateinit var lotto649ViewModel: Lotto649ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lotto649ViewModel =
            ViewModelProviders.of(this).get(Lotto649ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lotto649, container, false)
        val textView: TextView = root.findViewById(R.id.text_lotto649)
        lotto649ViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}