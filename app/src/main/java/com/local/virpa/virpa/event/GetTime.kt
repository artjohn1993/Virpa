package com.local.virpa.virpa.event

import java.text.SimpleDateFormat
import java.util.*



class GetTime {
    companion object {
        fun calculate(data : String) : String{
            var lala = data
            var result : String = ""

            val myDate = Date()
            var c = Calendar.getInstance()
            c.timeZone = TimeZone.getTimeZone("UTC")
            c.time = myDate
            val time = c.time
            var yearCurrent = SimpleDateFormat("yyyy", Locale.US).format(time).toInt()
            var monthCurrent = SimpleDateFormat("MM", Locale.US).format(time).toInt()
            var dayCurrent = SimpleDateFormat("dd", Locale.US).format(time).toInt()
            var hourCurrent = SimpleDateFormat("HH", Locale.US).format(time).toInt()
            var minuteCurrent = SimpleDateFormat("m", Locale.US).format(time).toInt()
            var secondsCurrent = SimpleDateFormat("ss", Locale.US).format(time).toDouble().toInt()

            var year = data.substring(0,4).toInt()
            var month = data.substring(5,7).toInt()
            var day = data.substring(8,10).toInt()
            var hour = data.substring(11,13).toInt()
            var minute = data.substring(14,16).toInt()
            var seconds = data.substring(17).toDouble().toInt()

            if (yearCurrent > year) {
                result = (yearCurrent - year).toString() + " yr"
            }
            else {
                if (monthCurrent > month) {
                    result = (monthCurrent - month).toString() + " mo"
                }
                else {
                    if (dayCurrent > day) {
                        result = (dayCurrent - day).toString() + " day"
                    }
                    else {
                        var tempHour = (hourCurrent - 8)
                        if (tempHour > hour) {
                            result = tempHour.toString() + " hr"
                        }
                        else {
                            if (minuteCurrent > minute) {
                                result = (minuteCurrent - minute).toString() + " min"
                            }
                            else {
                                result = (secondsCurrent - seconds).toString() + " sec"
                            }
                        }
                    }
                }
            }
        return result
        }
    }
}