package com.ahmety.newsapp

import com.ahmety.newsapp.utilities.formatDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DateExtensionsTest {

    @Test
    fun `test formatDate returns correctly formatted date`() {
        val testDate = "2024-10-18T12:34:56Z"
        val expectedFormattedDate = "18/10/2024"
        val result = testDate.formatDate()
        assertEquals(expectedFormattedDate, result)
    }
}