package com.example.easypokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var personagens: MutableList<Character>? = null
    private lateinit var queue: RequestQueue
    private var adapter: ModelAdapter? = null
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        personagens = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ModelAdapter(this, personagens as ArrayList<Person>)
        recyclerView.setAdapter(adapter)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        val endpoint = "https://pokeapi.co/api/v2/pokemon?limit=50"
        var pokeId = 0
        queue = Volley.newRequestQueue(this)

        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET, endpoint, null,
            { response ->
                try {
                    val results = response.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        pokeId += 1
                        val `object` = results.getJSONObject(i)
                        val person = Person()
                        person.name = `object`.getString("name").plus(" ${pokeId}")
                        person.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokeId}.png"
                        (personagens as ArrayList<Person>).add(person)
                    }
                    adapter!!.notifyDataSetChanged()
                } catch (ex: JSONException) {
                    Toast.makeText(this@MainActivity, "ERRO NO JSON: " + ex.message,
                        Toast.LENGTH_LONG).show()
                    Log.e("e/data",ex.message.toString())

                }
            }) { error ->
            Toast.makeText(this@MainActivity, "ERRO NA CONEXÃO À API: " + error.message,
                Toast.LENGTH_LONG).show()
        }
//
        queue.add(jsonObjectRequest)

    }
}

public infix fun Int.until(to: Int): IntRange {
    if (to <= Int.MIN_VALUE) return IntRange.EMPTY
    return this .. (to - 1).toInt()
}


