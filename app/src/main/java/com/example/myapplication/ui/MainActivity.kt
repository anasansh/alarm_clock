package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.adapter.AlarmAdapter
import com.example.myapplication.utils.AlarmDetailsDialog
import com.example.myapplication.utils.AlarmUtils
import com.example.myapplication.viewModel.AlarmViewModel
import com.example.myapplication.viewModel.AlarmViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AlarmViewModel
    private lateinit var alarmRecyclerView: RecyclerView
    private lateinit var addAlarmButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmRecyclerView = findViewById(R.id.alarm_recycler_view)
        addAlarmButton = findViewById(R.id.add_alarm_button)
        val factory =
            AlarmViewModelFactory(AlarmUtils(this))
        viewModel = ViewModelProvider(this, factory)[AlarmViewModel::class.java]

        viewModel.showToastEvent.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.alarms.observe(this) { alarms ->
            val adapter = AlarmAdapter(alarms)
            alarmRecyclerView.adapter = adapter
            alarmRecyclerView.layoutManager = LinearLayoutManager(this)
        }

        addAlarmButton.setOnClickListener {
            val dialog = AlarmDetailsDialog(this, viewModel)
            dialog.show()
        }
    }
}