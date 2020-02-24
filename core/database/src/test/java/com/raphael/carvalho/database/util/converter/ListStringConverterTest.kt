package com.raphael.carvalho.database.util.converter

import org.junit.Assert.assertEquals
import org.junit.Test

class ListStringConverterTest {

    @Test
    fun givenListSting_whenFromListString_thenReturnStringRepresentationOfListString() {
        val list = listOf("a", "b")

        val stringRepresentation = ListStringConverter()
            .fromListString(list)

        assertEquals("""["a","b"]""", stringRepresentation)
    }

    @Test
    fun givenEmptyListSting_whenFromListString_thenReturnEmptyStringRepresentation() {
        val list = emptyList<String>()

        val stringRepresentation = ListStringConverter()
            .fromListString(list)

        assertEquals("[]", stringRepresentation)
    }

    @Test
    fun givenStringRepresentationOfListString_whenConvertToListString_thenReturnListString() {
        val stringRepresentation = """["a","b"]"""

        val list = ListStringConverter()
            .toListString(stringRepresentation)

        assertEquals(listOf("a", "b"), list)
    }

    @Test
    fun givenEmptyStringRepresentation_whenConvertToListString_thenReturnEmptyListString() {
        val stringRepresentation = "[]"

        val list = ListStringConverter()
            .toListString(stringRepresentation)

        assertEquals(emptyList<String>(), list)
    }
}
