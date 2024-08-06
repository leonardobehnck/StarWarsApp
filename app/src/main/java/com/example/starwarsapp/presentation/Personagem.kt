package com.example.starwarsapp.presentation
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.R

class PersonagemActivity : AppCompatActivity() {
  lateinit var btnNext: Button
  lateinit var lista: ListView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_personagem)
    setupView()
    setupListeners()
    setupList()
  }

  fun setupView() {
    btnNext = findViewById(R.id.btnNext)
    lista = findViewById(R.id.lista)
  }

  fun setupList() {
    var dados = arrayOf(
      "name: Luke Skywalker",
    "height: 172",
    "mass: 77",
    "hair_color: blond",
    "skin_color: fair",
    "eye_color: blue",
    "birth_year: 19BBY",
    "gender: male",
    "homeworld: https://swapi.dev/api/planets/1/",
    )
    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, dados)
    lista.adapter = adapter
  }

  fun setupListeners() {
    btnNext.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }
  }
}


