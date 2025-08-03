package controller

import javafx.animation.AnimationTimer
import model.TimerData
import model.TimerState
import view.TimerView
import kotlin.time.Duration.Companion.seconds

class TimerController(private val view: TimerView) {
    var timerData = TimerData(0, 0)
    private var timer: AnimationTimer? = null   // Nullable

    // Функция высшего порядка (принимает лямбду)
    fun startTimer(onTick: (Int, Int) -> Unit) {
        if (timerData.minutes == 0 && timerData.seconds == 0) {
            throw IllegalArgumentException("Время не может быть 00:00!")  // Исключение
        }

        timerData.currentState = TimerState.RUNNING

        timer = object : AnimationTimer() {     // Анонимный класс (наследование)
            private var lastTick = 0L

            override fun handle(now: Long) {
                if (now - lastTick >= 1_000_000_000) {  // 1 секунда
                    lastTick = now
                    updateTime()
                    onTick(timerData.minutes, timerData.seconds)  // Вызов лямбды
                }
            }
        }.also { it.start() }
    }

    fun pauseTimer() {
        timerData.currentState = TimerState.PAUSED
        timer?.stop()  // Safe-call для nullable
    }

    fun resetTimer() {
        timerData = TimerData(0, 0, TimerState.STOPPED)
        timer?.stop()
        view.updateTime(0, 0)
    }

    private fun updateTime() {
        if (timerData.seconds == 0) {
            if (timerData.minutes == 0) {
                view.onTimerFinished()
                resetTimer()
                return
            }
            timerData.minutes--
            timerData.seconds = 59
        } else {
            timerData.seconds--
        }

        view.updateTime(timerData.minutes, timerData.seconds)
    }
}