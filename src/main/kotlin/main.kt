import controller.TimerController
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.stage.Stage
import model.TimerData
import view.TimerView

class TimerApp : Application(), TimerView {
    private val controller = TimerController(this)
    private val timeLabel = Label("00:00").apply { style = "-fx-font-size: 36;" }
    private val inputField = TextField().apply {
        promptText = "Секунды"
        maxWidth = 100.0  // Уменьшаем ширину поля ввода
    }

    override fun start(stage: Stage) {
        val buttons = listOf(
            Button("Старт").apply {
                setOnAction { startTimer() }
                style = "-fx-font-size: 14;"  // Уменьшаем шрифт кнопок
            },
            Button("Пауза").apply {
                setOnAction { controller.pauseTimer() }
                style = "-fx-font-size: 14;"
            },
            Button("Сброс").apply {
                setOnAction { controller.resetTimer() }
                style = "-fx-font-size: 14;"
            }
        )

        val root = VBox(15.0, timeLabel, inputField).apply {
            children.addAll(buttons)
            alignment = Pos.CENTER
        }

        stage.scene = Scene(root, 350.0, 300.0)  // Уменьшаем размер окна
        stage.title = "Таймер"
        stage.show()
    }

    private fun startTimer() {
        val seconds = inputField.text.toIntOrNull() ?: 0
        controller.timerData = TimerData(seconds / 60, seconds % 60)
        controller.startTimer { min, sec ->
            timeLabel.text = String.format("%02d:%02d", min, sec)
        }
    }

    override fun updateTime(minutes: Int, seconds: Int) {
        timeLabel.text = String.format("%02d:%02d", minutes, seconds)
    }

    override fun onTimerFinished() {
        timeLabel.text = "Готово!"
    }
}

fun main() {
    Application.launch(TimerApp::class.java)
}