package com.example.asistentevtt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import java.util.*

class SpeechToTextManager(private val context: Context) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var callback: ((String) -> Unit)? = null  // Callback para resultados

    init {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    }

    fun startListening(callback: (String) -> Unit) {
        this.callback = callback
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale("es", "ES"))  // Español
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        speechRecognizer?.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val transcribedText = matches?.get(0) ?: ""
                callback(transcribedText)
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Toast.makeText(context, "Error en STT: $error", Toast.LENGTH_SHORT).show()
                callback("")
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer?.startListening(intent)
    }

    fun destroy() {
        speechRecognizer?.destroy()
    }
}