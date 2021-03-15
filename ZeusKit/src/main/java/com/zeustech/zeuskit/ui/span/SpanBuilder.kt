package com.zeustech.zeuskit.ui.span

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

class SpanBuilder : SpannableStringBuilder() {

    override fun append(
        text: CharSequence,
        what: Any,
        flags: Int
    ): SpanBuilder {
        val start = length
        append(text)
        setSpan(what, start, length, flags)
        return this
    }

    private fun append(text: CharSequence, what: Any): SpanBuilder {
        val start = length
        append(text)
        setSpan(what, start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun appendColor(text: CharSequence, @ColorInt color: Int): SpanBuilder {
        return append(text, ForegroundColorSpan(color))
    }

    fun appendTypeface(text: CharSequence, typeface: Typeface): SpanBuilder {
        return append(text, SpanTypeface(typeface))
    }

    fun appendColorTypeface(
        text: CharSequence,
        typeface: Typeface,
        @ColorInt color: Int
    ): SpanBuilder {
        val start = length
        append(text)
        setSpan(ForegroundColorSpan(color), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(SpanTypeface(typeface), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }
}