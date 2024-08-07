package com.example.starwarsapp.data

import com.example.starwarsapp.domain.Character

class ChacaracterFactory {
  val list = listOf(
    Character(
      name = "LukeSkywalker",
      height = "172",
	    mass = "77",
	    hairColor = "blond",
	    skinColor = "fair",
	    eyeColor = "blue",
  	  birthYear = "19BBY",
	    gender = "male",
	    homeWorld = "https://swapi.dev/api/planets/1/",
    )
  )
}

