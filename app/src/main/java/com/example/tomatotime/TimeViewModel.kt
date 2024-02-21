package com.example.tomatotime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TimeViewModel : ViewModel() {
    var timeTotal: Long by mutableStateOf(0)

    var timeLeft: Long by mutableStateOf(0)

    fun updateTotalTime(text: String) {
        val value = text.toLong()
        timeTotal = value
        timeLeft = value
    }
}