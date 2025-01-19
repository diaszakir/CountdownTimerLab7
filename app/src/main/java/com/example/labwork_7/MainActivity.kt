package com.example.labwork_7

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var initialDuration: Int = 300
    private var duration: Int = initialDuration
    private lateinit var hour: TextView
    private lateinit var minute: TextView
    private lateinit var second: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private lateinit var resetButton: Button
    private lateinit var setTimeButton: Button
    private lateinit var inputHours: EditText
    private lateinit var inputMinutes: EditText
    private lateinit var inputSeconds: EditText
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hour = findViewById(R.id.hoursLeft)
        minute = findViewById(R.id.minuteLeft)
        second = findViewById(R.id.secondLeft)
        startButton = findViewById(R.id.startBtn)
        stopButton = findViewById(R.id.stopBtn)
        resetButton = findViewById(R.id.resetBtn)
        setTimeButton = findViewById(R.id.setTimeBtn)
        inputHours = findViewById(R.id.inputHours)
        inputMinutes = findViewById(R.id.inputMinutes)
        inputSeconds = findViewById(R.id.inputSeconds)

        updateTimerUI(duration * 1000L)

        setTimeButton.setOnClickListener {
            val hours = inputHours.text.toString().toIntOrNull() ?: 0
            val minutes = inputMinutes.text.toString().toIntOrNull() ?: 0
            val seconds = inputSeconds.text.toString().toIntOrNull() ?: 0

            duration = hours * 3600 + minutes * 60 + seconds
            initialDuration = duration

            countDownTimer?.cancel()
            isTimerRunning = false

            updateTimerUI(duration * 1000L)
        }

        startButton.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            }
        }

        stopButton.setOnClickListener {
            if (isTimerRunning) {
                countDownTimer?.cancel()
                isTimerRunning = false
            }
        }

        resetButton.setOnClickListener {
            countDownTimer?.cancel()
            isTimerRunning = false
            duration = initialDuration
            updateTimerUI(duration * 1000L)
        }
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(duration * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerUI(millisUntilFinished)
                duration = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                isTimerRunning = false
                updateTimerUI(0)
            }
        }.start()
        isTimerRunning = true
    }

    private fun updateTimerUI(millisUntilFinished: Long) {
        val time = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
        )
        val hourMinSec = time.split(":")
        hour.text = hourMinSec[0]
        minute.text = hourMinSec[1]
        second.text = hourMinSec[2]
    }
}
