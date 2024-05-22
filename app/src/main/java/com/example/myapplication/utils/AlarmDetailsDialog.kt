package com.example.myapplication.utils

import android.app.Dialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import com.example.myapplication.R
import com.example.myapplication.viewModel.AlarmViewModel

class AlarmDetailsDialog(context: Context, private val viewModel: AlarmViewModel) :
    Dialog(context) {

    private lateinit var timePicker: TimePicker
    private lateinit var labelEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_alarm_details)

        timePicker = findViewById(R.id.time_picker)
        labelEditText = findViewById(R.id.label_edit_text)
        saveButton = findViewById(R.id.save_button)

        timePicker.setIs24HourView(true)

        saveButton.setOnClickListener {
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute
            val label = labelEditText.text.toString().trim()


            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)
            val timeInMillis = calendar.timeInMillis
            viewModel.addAlarm(timeInMillis, label)
            dismiss()
        }
    }
}
