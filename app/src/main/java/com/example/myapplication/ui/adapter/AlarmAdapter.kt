package com.example.myapplication.ui.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.AlarmModel
import java.util.Date
import java.util.Locale

class AlarmAdapter(
    private val alarms: List<AlarmModel>,
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.timeTextView.text = formatTime(alarm.timeInMillis)
        holder.labelTextView.text = alarm.label
    }

    override fun getItemCount(): Int = alarms.size

    private fun formatTime(timeInMillis: Long): String {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return formatter.format(Date(timeInMillis))
    }

    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val timeTextView: TextView = itemView.findViewById(R.id.time_text_view)
        val labelTextView: TextView = itemView.findViewById(R.id.label_text_view)
    }

}
