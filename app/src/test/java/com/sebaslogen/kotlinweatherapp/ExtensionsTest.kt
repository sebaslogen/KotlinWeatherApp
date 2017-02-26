package com.sebaslogen.kotlinweatherapp

import com.sebaslogen.kotlinweatherapp.ui.utils.toDateString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat
import java.util.*

class ExtensionsTest {

    @Test fun testLongToDateString() {
        assertEquals("Oct 19, 2015", 1445275635000L.toDateString(locale = Locale.ENGLISH))
    }

    @Test fun testDateStringFullFormat() {
        assertEquals("Monday, October 19, 2015", 1445275635000L.toDateString(DateFormat.FULL, Locale.ENGLISH))
    }
}