package com.example.counterapplication

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _seconds = MutableLiveData<Int>()
    var finished = MutableLiveData(false)
    var startTime = MutableLiveData<Long>()
    private var countDownTimer: CountDownTimer? = null

    fun startTimer() {
        countDownTimer = object : CountDownTimer(startTime.value!!.toLong(), 1000) {
            override fun onTick(p0: Long) {
                _seconds.value = (p0 / 1000).toInt()
            }

            override fun onFinish() {
                finished.value = true
                countDownTimer?.cancel()
            }

        }.start()
    }

    fun getSeconds(): LiveData<Int> {
        return _seconds
    }

    fun stopTimer() {
        _seconds.value = 0
        countDownTimer?.cancel()
    }
    fun getCountDownTimer():CountDownTimer?{
        return countDownTimer
    }

}