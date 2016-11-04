package com.sebaslogen.kotlinweatherapp.ui.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import java.text.DateFormat
import java.util.*

val View.ctx: Context get() = context

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

var TextView.textColor: Int
    get() = currentTextColor
    set(c) = setTextColor(c)

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}