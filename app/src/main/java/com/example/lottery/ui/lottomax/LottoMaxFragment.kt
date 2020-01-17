package com.example.lottery.ui.lottomax

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lottery.MainActivity
import com.example.lottery.R
import java.text.SimpleDateFormat
import java.util.*

class LottoMaxFragment : Fragment() {

    private lateinit var lottoMaxViewModel: LottoMaxViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lottoMaxViewModel =
            ViewModelProvider(this).get(LottoMaxViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lottomax, container, false)
        val textView: TextView = root.findViewById(R.id.text_lottomax)
        val ids = arrayListOf<EditText>(root.findViewById(R.id.maxNumber1), root.findViewById(R.id.maxNumber2), root.findViewById(R.id.maxNumber3),
            root.findViewById(R.id.maxNumber4), root.findViewById(R.id.maxNumber5), root.findViewById(R.id.maxNumber6), root.findViewById(R.id.maxNumber7))
        val check: Button = root.findViewById(R.id.btnMaxCheck)
        val result: TextView = root.findViewById(R.id.textMaxNumbers)
        val numbers = arrayListOf<Int>()

        lottoMaxViewModel.text.observe(this, Observer {
            textView.text = it
            // When the check button is clicked
            check.setOnClickListener {
                // Clear the list in order to prevent adding numbers constantly
                numbers.clear()
                for (i in 0..6) {
                    val num = ids[i]
                    if (num.text.toString().isNullOrEmpty()) {
                        result.text = "The number of your numbers is less than 7."
                        break
                    } else if (num.text.toString().toInt() > 50) {
                        result.text = "Please enter the number (1~50)."
                    } else {
                        numbers.add(num.text.toString().toInt())
                        numbers.sort()
                        // Checking duplicated numbers or the number more than 50
                        if (numbers.distinct().size < 7) {
                            result.text = "You put duplicated numbers \nor the number(s) more than 50."
                        } else {
                            result.text = "Your numbers: " + numbers.distinct().joinToString()
                            // Close the keyboard
                            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                        }
                    }
                }
            }
        })
        return root
    }
}