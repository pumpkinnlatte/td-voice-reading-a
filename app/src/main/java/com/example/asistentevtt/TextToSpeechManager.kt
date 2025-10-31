package com.example.asistentevtt

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TextToSpeechManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech?.language = Locale("es", "ES")  // Español
            textToSpeech?.setSpeechRate(0.8f)  // Lento para niños
            isInitialized = true
        }
    }

    fun speak(text: String) {
        if (isInitialized && text.isNotEmpty()) {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    fun shutdown() {
        textToSpeech?.shutdown()
    }
}