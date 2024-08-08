package com.example.starwarsapp.ui
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class PersonagemActivity : AppCompatActivity() {
  lateinit var btnNext: Button
  lateinit var lista: ListView
  lateinit var btnBack: FloatingActionButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_personagem)
    setupView()
    setupListeners()
    setupList()
  }

  fun setupView() {
    btnNext = findViewById(R.id.btnNext)
    btnBack = findViewById(R.id.btnBack)
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
      //startActivity(Intent(this, MainActivity::class.java))
      GetCharacterInformation().execute("https://swapi.dev/api/people/?format=json")
    }

    btnBack.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }
  }

  //Classe para recuperar informação do personagem
  @Suppress("UNREACHABLE_CODE")
  inner class GetCharacterInformation : AsyncTask<String, String, String>() {

    //Sobrescreve a função, onPreExecute é chamada antes do restante
    override fun onPreExecute() {
      super.onPreExecute()
      Log.d("My task", "iniciando...")
    }

    //Função tratar URL, vararg espera string tipo "1", "2"
    override fun doInBackground(vararg url: String?): String {
      var urlConnection: HttpURLConnection? = null

      try {
        val urlBase = URL(url[0])



        urlConnection = urlBase.openConnection() as HttpURLConnection
        // timeout de 60.000ms
        urlConnection.connectTimeout = 60000
        urlConnection.readTimeout = 60000

        var response = urlConnection.inputStream.bufferedReader().use {it.readText()}
        publishProgress(response)

      } catch (_: Exception) {
        Log.e("Erro", "Erro ao realizar conexão.")
      } finally {
        urlConnection?.disconnect()
      }
      return " "

    }

    override fun onProgressUpdate(vararg values: String?) {
      try {
        // Implementação anterior
        //var json: JSONObject
        //values[0]?.let {
          //var json = JSONObject(it)

        val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
          for(i in 0 until jsonArray.length()) {

            val id = jsonArray.getJSONObject(i).getString("id")

            Log.d("ID:", id)
          }

      } catch (ex: Exception) {

      }
    }



    //Função não utilizada
    private fun streamToString(inputStream: InputStream) : String {
      val bufferReader = BufferedReader(InputStreamReader(inputStream))
      var line: String
      var result = ""

      try {
        do {
          line = bufferReader.readLine()
          line?.let{
            result += line
          }
        } while(true)
      } catch (ex: Exception) {
        Log.e("Erro", "Erro ao parcelar Stream")
      }
      return result
    }






  }
}




