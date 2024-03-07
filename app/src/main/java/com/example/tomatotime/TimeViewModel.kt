package com.example.tomatotime

import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {
    var timeTotal: MutableState<Long> = mutableStateOf(60 * 1000)

    var timeLeft: MutableState<Long> = mutableStateOf(60 * 1000)
    var timeAngle: MutableState<Double> = mutableStateOf(0.0)

    var startDountDown: MutableState<Boolean> = mutableStateOf(false)

    fun startCountDown() {
        countDownTimer.start()
        startDountDown.value = true
    }

    fun stopCountDown() {
        countDownTimer.cancel()
        startDountDown.value = false
    }

    private fun updateTotalTime(value: Long) {
        timeLeft.value = value
        timeAngle.value = value.toDouble() / timeTotal.value.toDouble() * 360.0
        System.out.println(timeAngle.value)
    }

    private var countDownTimer = object : CountDownTimer(timeTotal.value, 500/*ms*/) {
        override fun onTick(p0: Long) {
            updateTotalTime(timeLeft.value - 500)
        }

        override fun onFinish() {
            System.out.println("finished")
        }
    }
}