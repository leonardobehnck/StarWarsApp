package com.example.starwarsapp.ui
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.starwarsapp.R
import com.example.starwarsapp.domain.Character
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PersonagemActivity : AppCompatActivity() {

  lateinit var btnBackBottom: Button
  lateinit var lista: ListView
  lateinit var spinner: Spinner
  lateinit var btnBackbtnBackTop: FloatingActionButton
  lateinit var progress: ProgressBar

  var characterList : ArrayList<Character> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_person)
    callService()
    setupView()
    setupListeners()
    checkForInternet()

    val checkInternet = checkForInternet()
    Log.d("There is internet?:", checkInternet.toString())
  //setupSpinner()
  }

  fun setupView() {
    btnBackBottom = findViewById(R.id.btnBackBottom)
    btnBackbtnBackTop = findViewById(R.id.btnBackTop)
    lista = findViewById(R.id.lista)
    progress = findViewById(R.id.tbLoader)
    //spinner = findViewById(R.id.spinner)
  }

  fun setupSpinner() {
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, characterList)
    spinner.adapter = adapter
  }

  fun setupList() {
    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, characterList)
    lista.adapter = adapter
    progress.visibility = VISIBLE
  }

  fun callService() {
    val urlBase = "https://swapi.dev/api/people/?format=json"
      GetCharacterInformation().execute(urlBase)
  }

  fun setupListeners() {
    btnBackBottom.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }

    btnBackbtnBackTop.setOnClickListener {
      startActivity(Intent(this, MainActivity::class.java))
    }
  }

  fun checkForInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val network = connectivityManager.activeNetwork ?: return false
      val activeNetwork = connectivityManager.getNetworkCapabilities((network)) ?: return false
      return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
      }
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
      }
    }


  //Classe para recuperar informação do personagem
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
    values[0]?.let {
    try {
        val jsonObject = JSONObject(it)
        val resultsArray = jsonObject.getJSONArray("results")
        for (i in 0 until resultsArray.length()) {
          val character = resultsArray.getJSONObject(i)
          val name = character.getString("name")
          val height = character.getString("height")
          val mass = character.getString("mass")
          val hairColor = character.getString("hair_color")
          val skinColor = character.getString("skin_color")
          val eyeColor =  character.getString("eye_color")
          val birthYear = character.getString("birth_year")
          val gender =  character.getString("gender")
          val homeWorld = character.getString("homeworld")

            val model = Character (
              name = name,
              height = height,
              mass = mass,
              hairColor = hairColor,
              skinColor = skinColor,
              eyeColor = eyeColor,
              birthYear = birthYear,
              gender = gender,
              homeWorld = homeWorld
            )
          characterList.add(model)
        }

     // Depois de carregar os dados da API chama função para desenhar na tela
      lista.visibility = VISIBLE
      setupList()
      progress.visibility = GONE
    } catch (e: JSONException) {
        Log.e("JSON Error", "Error parsing JSON", e)
          }
        }
      }
    }
}





