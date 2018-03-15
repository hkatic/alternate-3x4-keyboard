package com.hrvojekatic.a3x4

import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class Speech(private val _context: Context) : TextToSpeech.OnInitListener {
	private lateinit var tts: TextToSpeech
	private lateinit var audioManager: AudioManager
	private var ttsCanSpeak = false

	init {
		audioManager = _context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
		tts = TextToSpeech(_context, this)
	}

	override fun onInit(status: Int) = if (status == TextToSpeech.SUCCESS) {
		setUtteranceProgressListener()
		val result = tts?.setLanguage(Locale.getDefault())
		ttsCanSpeak = !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
	} else {
		ttsCanSpeak = false
	}

	private fun setUtteranceProgressListener() {
		tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
			override fun onStart(utteranceId: String) {
				when {
					Build.VERSION.SDK_INT < Build.VERSION_CODES.O -> audioManager?.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
				}
			}

			override fun onDone(utteranceId: String) {
				audioManager.abandonAudioFocus(audioFocusChangeListener)
				when (utteranceId) {
					"ID_WAIT" -> tts.stop()
				}
			}

			override fun onError(utteranceId: String) {}
		})
	}

	private val audioFocusChangeListener = object : AudioManager.OnAudioFocusChangeListener {
		override fun onAudioFocusChange(focusChange: Int) {
			if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
				audioManager.abandonAudioFocus(this)
			}
		}
	}

	fun speak(text: String, mode: Int = TextToSpeech.QUEUE_FLUSH, utteranceId: String = "ID_MESSAGE") {
		when {
			!ttsCanSpeak -> return
			else -> when {
				Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
					val params = Bundle()
					params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
					tts.speak(text, mode, params, utteranceId)
				}
				Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP -> {
					val speechMap = HashMap<String, String>()
					speechMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId)
					tts.speak(text, mode, speechMap)
				}
			}
		}
	}

	fun speakCharacter(character: CharSequence) {
		if (character.isEmpty()) {
			return
		}
		if (character.first().isLetter() && character.first().isUpperCase()) {
			tts.setPitch(3.0f)
			speak(character.toString())
			tts.setPitch(1.0f)
		} else {
			speak(character.toString().symbolsToWords(_context))
		}
	}

	fun shutdown() = tts.let {
		it.stop()
		it.language = Locale.getDefault()
		it.shutdown()
	}
}
