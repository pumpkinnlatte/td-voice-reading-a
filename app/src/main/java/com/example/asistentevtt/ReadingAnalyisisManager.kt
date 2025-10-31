package com.example.asistentevtt

import org.apache.commons.text.similarity.LevenshteinDistance

class ReadingAnalysisManager {

    fun analyze(originalText: String, transcribedText: String, durationMs: Long): ReadingResult {
        // Precisión
        val distance = LevenshteinDistance().apply(originalText, transcribedText)
        val maxLen = maxOf(originalText.length, transcribedText.length)
        val precisionScore = if (maxLen == 0) 100 else ((maxLen - distance) * 100 / maxLen)

        // Fluidez (WPM)
        val wordCount = transcribedText.split(" ").size
        val minutes = durationMs / 60000.0
        val fluencyScore = if (minutes > 0) (wordCount / minutes).toInt() else 0
        val normalizedFluency = minOf(fluencyScore, 100)

        return ReadingResult(precisionScore, normalizedFluency)
    }

    data class ReadingResult(val precisionScore: Int, val fluencyScore: Int) {
        fun precisionStars(): String = scoreToStars(precisionScore)
        fun fluencyStars(): String = scoreToStars(fluencyScore)

        private fun scoreToStars(score: Int): String = "⭐".repeat((score / 20) + 1).takeIf { it.isNotEmpty() } ?: "⭐"
    }
}