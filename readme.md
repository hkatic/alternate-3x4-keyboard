# Alternate 3X4 Keyboard for Android #

## Introduction

I was unsatisfied how TalkBack screen reader for Android works with 3X4 soft keyboards that can be found on some Android devices such as Samsung. Many Android devices even don't have integrated 3X4 keyboard as a choice. Since I'm definitely faster on typing with old-style 3X4 keyboard, I've decided to make my own.

Alternate 3X4 Keyboard is a self-voicing soft keyboard for Android, with classic 3X4 layout, like the one you know from old keyboard phones such as Nokia, Siemens or Ericsson. Since it is self-voicing, it doesn't require TalkBack to be running for it to work. The main reason why I've decided to make it TalkBack-independent is because I wanted to customize how echoing of character will work, how capital letters will be announced, how character deletion will be announced, how selections will be announced, etc.

For optimal performanse and stability, it is advised to pause TalkBack temporarily, either by long-pressing both volume keys on your device, or pausing TalkBack from it's global context menu. Otherwise, you'll get double echoing of characters, and probably some other bad behavior.

Note, this app is pretty much under development, and many features are still lacking. If you're willing to contribute, please do so by creating pull requests. Your help is always appreciated, since I also have my personal life, job and other stuff, and this is just my little hoby project that I can work on when I get enough free time!

## Requirements

* A physical Android device is required. This app should work on Android 4.0 Ice Cream Sandwich or later.
* Latest Android SDK with Gradle build system is required for building from command line.
* Use Android Studio as an alternative if you wanna make your life easier. I've built this app with Android Studio 3.0.1, which is currently the latest version. Just make sure that all SDK components are installed, including all Android platforms from API 27 up to ICS.
* Android SDK Build tools 27.0.3 are required.
* Java Development Kit (JDK) 8 is required.
* This app is written in Kotlin, so you'll need the latest version of Kotlin programming language as well. If you're using Android Studio, you only have to make sure that your SDK components including Android Studio itself are up-to-date.

## Building from source

Note, please enable developer options on your device first, by tapping build number option multiple times. On most Android devices, this option can be found under System Settings, About category. Then go to developer options under System Settings and enable USB debugging.

To build from command line with gradle, read the instructions [https://developer.android.com/studio/build/building-cmdline.html](Here).

To build with Android Studio, Open the project from this repository, then press Shift+F10. Select your device, then click OK. If everything is configured properly, the app should be installed and running on your device.

## My current future plans

Here are my top features that I'm planning to implement in the future:

* Customizing speech, including speaking of characters and words, as well as configuring speech verbosity for the keyboard.
* Implementing navigation and reading modes.
* Implementing sound themes for the keyboard.
* Implementing multiple keyboard layouts for various needs.
* Customizing keyboard size for various types of fingers.
