package com.example.m1lesson60volley

import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.m1lesson60volley.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var requestQueue: RequestQueue
    private val imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)

        binding.apply {


            // image ni urldan yuklab olish
            val imageRequest = ImageRequest(
                imageUrl,
                object : Response.Listener<Bitmap> {
                    override fun onResponse(response: Bitmap?) {
                        if (response != null) {
                            img.setImageBitmap(response)
                        }
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                })

            requestQueue.add(imageRequest)

            // api dan bitta object malumotlarini olib kelish

            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                "https://jsonplacejolder.typicode.com/users/1",
                null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        if (response != null) {
                            Log.d(TAG, "onResponse: $response")
                            val email = response.get("email")
                            Log.d(TAG, "onResponse: $email")
                        }
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Log.d(TAG, "onErrorResponse: ${error?.message}")
                    }
                }

            )

            requestQueue.add(jsonObjectRequest)


            btn.setOnClickListener {

                val jsonArrayRequest = JsonArrayRequest(
                    Request.Method.GET,
                    "https://jsonplacejolder.typicode.com/users",
                    null,
                    object : Response.Listener<JSONArray> {
                        override fun onResponse(response: JSONArray?) {
                            Log.d(TAG, "onResponse: $response")
                        }
                    },
                    object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {
                            Log.d(TAG, "onErrorResponse: ${error?.message}")
                        }
                    }
                )

                requestQueue.add(jsonArrayRequest)

            }

        }
    }


    /*
    // checking internet
    private fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

     */
}