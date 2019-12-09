package com.example.lottery.ui.storage

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lottery.R

class StorageFragment : Fragment() {

    companion object {
        fun newInstance() = StorageFragment()
    }

    private lateinit var viewModel: StorageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.storage_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StorageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
