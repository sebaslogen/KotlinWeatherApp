package com.sebaslogen.kotlinweatherapp

import com.sebaslogen.kotlinweatherapp.ui.utils.toDateString
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DateFormat

class ExtensionsTest {

    @Test fun testLongToDateString() {
        assertEquals("19-oct-2015", 1445275635000L.toDateString())
    }

    @Test fun testDateStringFullFormat() {
        assertEquals("lunes 19 de octubre de 2015", 1445275635000L.toDateString(DateFormat.FULL))
    }
}