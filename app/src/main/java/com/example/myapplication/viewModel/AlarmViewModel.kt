package com.example.myapplication.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.AlarmModel
import com.example.myapplication.utils.AlarmUtils

class AlarmViewModel(private val alarmManagerUtil: AlarmUtils) : ViewModel() {

    private val _alarms = MutableLiveData<List<AlarmModel>>(emptyList())
    val alarms: LiveData<List<AlarmModel>> = _alarms
    private val _showToast = MutableLiveData<String>()
    val showToastEvent: LiveData<String> get() = _showToast

    private fun showToast(message: String) {
        _showToast.value = message
    }

    fun addAlarm(timeInMillis: Long, label: String) {
        if (timeInMillis < System.currentTimeMillis()) {
            showToast("PLease select a time into future")
            return
        }
        val newAlarmId = getNextAlarmId()
        val newAlarm = AlarmModel(newAlarmId, timeInMillis, label)
        _alarms.value = _alarms.value?.plus(listOf(newAlarm))
        alarmManagerUtil.setAlarm(newAlarm)
    }

    private var currentId = 0

    private fun getNextAlarmId(): Int {

        return ++currentId;
    }
}

class AlarmViewModelFactory(private val alarmManagerUtil: AlarmUtils) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            return AlarmViewModel(alarmManagerUtil) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
