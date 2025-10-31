package com.example.asistentevtt

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextInput: EditText
    private lateinit var textViewTranscription: TextView
    private lateinit var textViewPrecision: TextView
    private lateinit var textViewFluency: TextView

    private lateinit var ttsManager: TextToSpeechManager
    private lateinit var sttManager: SpeechToTextManager
    private lateinit var analysisManager: ReadingAnalysisManager

    private var originalText = ""
    private var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        editTextInput = findViewById(R.id.editTextInput)
        textViewTranscription = findViewById(R.id.textViewTranscription)
        textViewPrecision = findViewById(R.id.textViewPrecision)
        textViewFluency = findViewById(R.id.textViewFluency)

        // Inicializar managers
        ttsManager = TextToSpeechManager(this)
        sttManager = SpeechToTextManager(this)
        analysisManager = ReadingAnalysisManager()

        // Botón TTS
        findViewById<Button>(R.id.buttonTTS).setOnClickListener {
            val text = editTextInput.text.toString()
            if (text.isNotEmpty()) {
                ttsManager.speak(text)
                originalText = text
            }
        }

        // Botón STT
        findViewById<Button>(R.id.buttonSTT).setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
            } else {
                startTime = System.currentTimeMillis()
                sttManager.startListening { transcribedText ->
                    textViewTranscription.text = "Transcripción: $transcribedText"
                    val result = analysisManager.analyze(originalText, transcribedText, System.currentTimeMillis() - startTime)
                    textViewPrecision.text = "Precisión: ${result.precisionStars()} (${result.precisionScore}%)"
                    textViewFluency.text = "Fluidez: ${result.fluencyStars()} (${result.fluencyScore} WPM)"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsManager.shutdown()
        sttManager.destroy()
    }
}