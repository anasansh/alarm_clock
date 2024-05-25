package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.adapter.AlarmAdapter
import com.example.myapplication.utils.AlarmDetailsDialog
import com.example.myapplication.utils.AlarmUtils
import com.example.myapplication.viewModel.AlarmViewModel
import com.example.myapplication.viewModel.AlarmViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AlarmViewModel
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val factory = AlarmViewModelFactory(AlarmUtils(this))
        viewModel = ViewModelProvider(this, factory)[AlarmViewModel::class.java]

        viewModel.showToastEvent.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.alarms.observe(this) { alarms ->
            val adapter = AlarmAdapter(alarms)
            binding.alarmRecyclerView.adapter = adapter
            binding.alarmRecyclerView.layoutManager = LinearLayoutManager(this)
        }

        binding.addAlarmButton.setOnClickListener {
            val dialog = AlarmDetailsDialog(this, viewModel)
            dialog.show()
        }
    }
}