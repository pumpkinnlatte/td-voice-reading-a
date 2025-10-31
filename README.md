# td-voice-reading-a
Una aplicación Android diseñada para ayudar a niños de 2 a 8 años a mejorar sus habilidades de lectura. Utiliza tecnología de voz para leer textos en voz alta (Text-to-Speech) y analizar la pronunciación del niño mediante reconocimiento de voz (Speech-to-Text), evaluando precisión y fluidez de manera simple y motivadora.

## Características Principales

- **Lectura en Voz Alta (TTS)**: La app lee el texto seleccionado en voz alta para que el niño pueda escucharlo como referencia.
- **Análisis de Lectura**: Graba la voz del niño, transcribe su lectura y calcula precisión (basada en similitud con el texto original) y fluidez (palabras por minuto).
- **Evaluación Motivadora**: Muestra resultados con estrellas (1-5) para fomentar el aprendizaje sin frustración.
- **Offline**: Funciona completamente sin conexión a internet, respetando la privacidad.
- **Idioma Español**: Configurada para español (España), ideal para usuarios hispanohablantes.

## Cómo Funciona

1. **Selección de Texto**: El niño ingresa una frase corta (máx. 50 palabras) en un campo de texto.
2. **Lectura de Referencia**: Presiona "Leer en Voz Alta" para escuchar la pronunciación correcta.
3. **Grabación y Análisis**: Presiona "Grabar y Analizar" para leer en voz alta. La app graba (<20 segundos), transcribe y evalúa.
4. **Resultados**: Muestra transcripción, precisión y fluidez con estrellas y puntajes porcentuales.

## Cálculo de Precisión

La precisión mide qué tan bien el niño reproduce el texto original, comparando la transcripción de su voz con el texto escrito.

- **Método**: Utiliza la **distancia de Levenshtein** (de la librería Apache Commons Text), que calcula el número mínimo de ediciones (inserciones, eliminaciones o sustituciones) para transformar la transcripción en el texto original.
- **Fórmula**:
  - Distancia = Levenshtein(original, transcripción)
  - Longitud máxima = máx(longitud(original), longitud(transcripción))
  - Puntaje de Precisión = ((Longitud máxima - Distancia) / Longitud máxima) * 100
- **Ejemplo**: Si el texto original es "El gato duerme" y la transcripción es "El gato durme", la distancia es 1 (cambio de 'e' por 'i'). Si longitud máxima es 13, precisión ≈ 92%.
- **Escala**: Puntaje interno 1-100, convertido a estrellas (1-5) para la UI: 1-20% = 1 estrella, 21-40% = 2, etc.

Esto evalúa errores de pronunciación, omisiones o agregados, pero no considera entonación (fuera del scope actual).

## Cálculo de Fluidez

La fluidez mide la velocidad y suavidad de la lectura.

- **Método**: Calcula **palabras por minuto (WPM)** basadas en la duración de la grabación y el conteo de palabras en la transcripción.
- **Fórmula**:
  - Conteo de palabras = número de palabras en la transcripción.
  - Minutos = duración de grabación en milisegundos / 60,000.
  - WPM = Conteo de palabras / Minutos (si minutos > 0, sino 0).
- **Normalización**: Limita a 100 WPM máximo para evitar inflar puntajes.
- **Escala**: Puntaje interno 1-100 (igual a WPM normalizado), convertido a estrellas (1-5) linealmente.
- **Nota**: No detecta pausas explícitamente en este version, pero una fluidez baja puede indicar vacilaciones.

## Requisitos

- **Android**: Mínimo SDK 21 (Android 5.0).
- **Permisos**: Acceso al micrófono (RECORD_AUDIO).
- **Idioma**: Dispositivo con soporte para español en TTS/STT (incluido por defecto en Android).
- **Dependencias**: Android APIs nativas + Apache Commons Text para análisis.

## Instalación

1. Clona este repositorio: `git clone https://github.com/tu-usuario/asistentevtt.git`
2. Abre en Android Studio.
3. Construye y ejecuta en un dispositivo/emulador.
4. Otorga permisos de micrófono cuando se solicite.

## Uso

1. Ingresa una frase corta.
2. Presiona "Leer en Voz Alta" para referencia.
3. Presiona "Grabar y Analizar" y lee.
4. Revisa los resultados y repite para practicar.


- Texto limitado a frases cortas para niños pequeños.
- No incluye análisis de entonación o pausas avanzadas.
- Posibles mejoras: Persistencia de resultados, más idiomas, detección de pausas con audio processing.
