package com.hrvojekatic.a3x4

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.media.AudioManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputConnection

class A3x4ImeService : InputMethodService(), OnKeyboardActionListener {
	private lateinit var deletedCharacters: CharSequence
	private lateinit var typedCharacters: CharSequence
	private lateinit var keyboardView: KeyboardView
	private lateinit var keyboard: Keyboard
	private var caps = false
	private lateinit var capsState: String
	private lateinit var audioManager: AudioManager
	private lateinit var speech: Speech

	override fun onInitializeInterface() {
		speech = Speech(getApplicationContext())
	}

	override fun onCreateInputView(): View {
		keyboardView = layoutInflater.inflate(R.layout.keyboard, null) as KeyboardView
		keyboard = Keyboard(this, R.xml.layout_3x4)
		keyboardView.keyboard = keyboard
		keyboardView.setPreviewEnabled(false)
		keyboardView.setOnKeyboardActionListener(this)
		return keyboardView
	}

	private fun playClick(keyCode: Int) {
		audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		when (keyCode) {
			32 -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
			Keyboard.KEYCODE_DONE, 10 -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
			Keyboard.KEYCODE_DELETE -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE)
			else -> audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
		}
	}

	private fun echoKey(keyCode: Int) {
		when (keyCode) {
			32 -> speech.speak(getString(R.string.symbol_space))
			Keyboard.KEYCODE_SHIFT -> speech.speak(getString(R.string.key_shift, capsState))
			Keyboard.KEYCODE_DELETE -> speech.speakCharacter(deletedCharacters)
			Keyboard.KEYCODE_DONE, 10 -> speech.speak(getString(R.string.key_done))
			else -> speech.speakCharacter(typedCharacters)
		}
	}

	private fun handleShift() {
		caps = !caps
		keyboard.isShifted = caps
		keyboardView.invalidateAllKeys()
		capsState = if (caps) getString(R.string.on) else getString(R.string.off)
	}

	private fun handleDelete(inputConnection: InputConnection) {
		val selectedText = inputConnection.getSelectedText(0)
		if (TextUtils.isEmpty(selectedText)) {
			inputConnection.deleteSurroundingText(1, 0)
		} else {
			inputConnection.commitText("", 1)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		speech.shutdown()
	}

	override fun onKey(primaryCode: Int, keyCodes: IntArray) {
		val inputConnection = currentInputConnection
		deletedCharacters = inputConnection.getTextBeforeCursor(1, 0)
		when (primaryCode) {
			Keyboard.KEYCODE_DELETE -> handleDelete(inputConnection)
			Keyboard.KEYCODE_SHIFT -> handleShift()
			Keyboard.KEYCODE_DONE -> inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KEYCODE_ENTER))
			else -> {
				var code = primaryCode.toChar()
				if (Character.isLetter(code) && caps) {
					code = Character.toUpperCase(code)
				}
				typedCharacters = code.toString()
				inputConnection.commitText(code.toString(), 1)
			}
		}
	}

	override fun onPress(primaryCode: Int) {
		playClick(primaryCode)
	}

	override fun onRelease(primaryCode: Int) {
		echoKey(primaryCode)
	}

	override fun onText(text: CharSequence) {}

	override fun swipeDown() {}

	override fun swipeLeft() {}

	override fun swipeRight() {}

	override fun swipeUp() {}
}
