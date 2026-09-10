package com.metes.worthit.core.database.converter

import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class BigDecimalConverterTest {

    private lateinit var converter: BigDecimalConverter

    @Before
    fun setup() {
        converter = BigDecimalConverter()
    }

    @Test
    fun `bigDecimalToString returns correct positive value as String`() {
        val bigDecimal = BigDecimal.valueOf(1999)
        assertEquals("1999", converter.bigDecimalToString(bigDecimal))
    }

    @Test
    fun `stringToBigDecimal returns correct positive value as BigDecimal`() {
        val string = "1999"
        assertEquals(BigDecimal.valueOf(1999), converter.stringToBigDecimal(string))
    }

    @Test
    fun `bigDecimalToString returns correct negative value as String`() {
        val bigDecimal = BigDecimal.valueOf(-1999)
        assertEquals("-1999", converter.bigDecimalToString(bigDecimal))
    }

    @Test
    fun `stringToBigDecimal returns correct negative value as BigDecimal`() {
        assertEquals(BigDecimal.valueOf(-1999), converter.stringToBigDecimal("-1999"))
    }

    @Test
    fun `bigDecimalToString returns null when BigDecimal value is null`() {
        assertEquals(null, converter.bigDecimalToString(null))
    }

    @Test
    fun `stringToBigDecimal returns null when String value is null`() {
        assertEquals(null, converter.stringToBigDecimal(null))
    }
}
