package view

interface TimerView {
    fun updateTime(minutes: Int, seconds: Int)  // Абстрактный метод
    fun onTimerFinished()                       // Колбэк
}