package com.raphael.carvalho.api.test

object Resource {
    /**
     * read from [fileLocation] and returns the content
     */
    fun read(fileLocation: String) = javaClass
        .classLoader!!
        .getResourceAsStream(fileLocation)!!
        .bufferedReader()
        .use { it.readText() }
}
