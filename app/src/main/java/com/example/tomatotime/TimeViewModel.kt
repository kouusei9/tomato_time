package com.example.tomatotime

import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {
    var timeTotal: MutableState<Long> = mutableStateOf(60 * 1000)
    var timeLeft: MutableState<Long> = mutableStateOf(60 * 1000)
    var timeAngle: MutableState<Double> = mutableStateOf(0.0)
    var timeFormat: MutableState<String> = mutableStateOf("60 S")

    var showDialog: MutableState<Boolean> = mutableStateOf(false)

    val GAP_TIME: Long = 100

    var startDountDown: MutableState<Boolean> = mutableStateOf(false)

    fun setUpDefaultInfo(timeTotal: Long) {
        settingNewTotalTime(timeTotal)
    }

    fun startCountDown() {
        countDownTimer.start()
        startDountDown.value = true
    }

    fun stopCountDown() {
        countDownTimer.cancel()
        startDountDown.value = false
    }

    fun reset() {
        countDownTimer.cancel()
        startDountDown.value = false
        updateTotalTime(timeTotal.value)
    }

    fun showDialog() {
        showDialog.value = true
    }

    fun dismissDialog() {
        showDialog.value = false
    }

    fun settingNewTotalTime(value: Long) {
        timeTotal.value = value * 1000
        startDountDown.value = false
        updateTotalTime(value * 1000)
        dismissDialog()
    }

    private fun updateTotalTime(value: Long) {
        timeLeft.value = value
        timeAngle.value = value.toDouble() / timeTotal.value.toDouble() * 360.0
        timeFormat.value = "${value / 1000} S"
        System.out.println(timeAngle.value)
    }

    private var countDownTimer = object : CountDownTimer(timeTotal.value, GAP_TIME/*ms*/) {
        override fun onTick(p0: Long) {
            updateTotalTime(timeLeft.value - GAP_TIME)
        }

        override fun onFinish() {
            System.out.println("finished")
            cancel()
        }
    }
}