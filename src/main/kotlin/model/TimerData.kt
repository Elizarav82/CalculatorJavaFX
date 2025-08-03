package model

data class TimerData(
    var minutes: Int,
    var seconds: Int,
    var currentState: TimerState? = null  // Nullable тип
)