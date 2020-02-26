package com.raphael.carvalho.database.util.converter

import org.junit.Assert.assertEquals
import org.junit.Test

class ListLongConverterTest {

    @Test
    fun givenListLong_whenFromListLong_thenReturnStringRepresentationOfListLong() {
        val list = listOf<Long>(1, 10)

        val stringRepresentation = ListLongConverter()
            .fromListLong(list)

        assertEquals("""[1,10]""", stringRepresentation)
    }

    @Test
    fun givenEmptyList_whenFromListLong_thenReturnEmptyStringRepresentation() {
        val list = emptyList<Long>()

        val stringRepresentation = ListLongConverter()
            .fromListLong(list)

        assertEquals("[]", stringRepresentation)
    }

    @Test
    fun givenStringRepresentationOfListLong_whenConvertToListLong_thenReturnListLong() {
        val stringRepresentation = """[1,10]"""

        val list = ListLongConverter()
            .toListLong(stringRepresentation)

        assertEquals(listOf<Long>(1, 10), list)
    }

    @Test
    fun givenEmptyStringRepresentation_whenConvertToListLong_thenReturnEmptyListLong() {
        val stringRepresentation = "[]"

        val list = ListLongConverter()
            .toListLong(stringRepresentation)

        assertEquals(emptyList<String>(), list)
    }
}
