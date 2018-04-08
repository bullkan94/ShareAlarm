package com.example.bullk.myapplication

import org.jetbrains.anko.db.classParser
import java.text.SimpleDateFormat
import java.util.*

class AlarmInfo (private val requestCode : Int, private val time : Long, private val dayOfWeek: Int,
                 private val isRepeat : String) {
    companion object {
        const val MONDAY = 1
        const val TUESDAY = 2
        const val WEDNESDAY = 4
        const val THURSDAY = 8
        const val FRIDAY = 16
        const val SATURDAY = 32
        const val SUNDAY = 64
    }

    override fun toString() : String {
        val date = Date(time)
        val start = SimpleDateFormat("yyyy.MM.dd").format(date)
        val time = SimpleDateFormat("HH : mm").format(date)
        return "requestCode : $requestCode, time : $time" +
                ", StartDay : $start. dayOfWeek : ${getDayOfWeek(dayOfWeek)}, isRepeat = $isRepeat"
    }

    private fun getDayOfWeek(num : Int) : String {
        if(isRepeat == "false")
            return "반복없음"
        var result = ""

        for(i in 0..6) {
            val mask = 1 shl i
            when(num and mask) {
                MONDAY -> result += "월"
                TUESDAY -> result += "화"
                WEDNESDAY -> result += "수"
                THURSDAY -> result += "목"
                FRIDAY -> result += "금"
                SATURDAY -> result += "토"
                SUNDAY -> result += "일"
            }
        }

        return result
    }
}