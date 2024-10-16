package org.example.mytabata

import android.os.CountDownTimer
import android.util.Log

class CounterDown(var segundos: Int,var loQueHaceAlHacertick: (Long) -> Unit){
    var myCounter: Any
    var counterState : Boolean = false

    init{
         myCounter = object : CountDownTimer(((segundos + 1000L).toLong()), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                loQueHaceAlHacertick(millisUntilFinished / 1000L)

            }

            override fun onFinish() {
                counterState = false
            }
        }
    }
}
