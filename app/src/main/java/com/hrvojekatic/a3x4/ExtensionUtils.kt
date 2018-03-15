package com.hrvojekatic.a3x4

import android.content.Context

fun String.symbolsToWords(context: Context): String = this.replace(" ", context.getString(R.string.symbol_space))
		.replace("\r", context.getString(R.string.symbol_return))
		.replace("\n", context.getString(R.string.symbol_line_feed))
		.replace(".", context.getString(R.string.symbol_period))
		.replace(",", context.getString(R.string.symbol_comma))
		.replace("?", context.getString(R.string.symbol_question_mark))
		.replace("!", context.getString(R.string.symbol_exclamation_mark))
		.replace("@", context.getString(R.string.symbol_at_sign))
		.replace("#", context.getString(R.string.symbol_number_sign))

