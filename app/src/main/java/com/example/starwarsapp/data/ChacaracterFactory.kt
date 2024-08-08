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


//HTTP
// - VERBO GET -> Recuperar informações
// - POST -> Enviar informações para servidor
// - DELETE -> Deletar algum recurso
// - PUT -> Alterar uma entidade como um todo
// - PATCH -> Alterar um atributo da entidade
//
