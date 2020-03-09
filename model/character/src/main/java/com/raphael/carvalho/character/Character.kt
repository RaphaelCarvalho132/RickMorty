package com.raphael.carvalho.character

interface Character {
    val id: Int
    val name: String
    val status: String
    val species: String
    val type: String
    val gender: String
    val origin: Origin
    val location: Location
    val image: String
    val episode: List<Long>
    val created: String
}
