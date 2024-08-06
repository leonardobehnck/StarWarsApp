package com.example.starwarsapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.starwarsapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
  lateinit var btnNext: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    setupView()
    setupListeners()
  }
  fun setupView() {
    btnNext = findViewById(R.id.btnNext)
  }

  fun setupListeners() {
    btnNext.setOnClickListener {
      startActivity(Intent(this, PersonagemActivity::class.java))
    }
  }
}


data class ApiResponse(val name: String)

interface ApiService {
  @GET("people/1")
  fun getData(): Call<ApiResponse>
}

class ApiClient {
  private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://swapi.dev/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  val service: ApiService = retrofit.create(ApiService::class.java)
}

fun fetchData() {
  val apiClient = ApiClient()
  apiClient.service.getData().enqueue(object : Callback<ApiResponse> {
    override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
      if (response.isSuccessful) {
        val data = response.body()
        Log.d("API Response", "Data: $data")
      } else {
        Log.e("API Error", "Error: ${response.code()}")
      }
    }

    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
      Log.e("API Failure", "Failed to fetch data", t)
    }
  })
}
