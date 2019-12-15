package com.example.lottery.db

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lottery.R
import com.example.lottery.db.model.Numbers

class NumberAdapter(private val context: Context, private val numberList: List<Numbers>) :
RecyclerView.Adapter<NumberAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_storage, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(numberList[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        private val textDate = itemView?.findViewById<TextView>(R.id.text_Date)
        private val textNumbers = itemView?.findViewById<TextView>(R.id.text_Numbers)
        private val textType = itemView?.findViewById<TextView>(R.id.text_Type)

        fun bind (numbers: Numbers) {
            textDate?.text = numbers.date
            textNumbers?.text = numbers.numbers
            textType?.text = numbers.type
        }
    }
}