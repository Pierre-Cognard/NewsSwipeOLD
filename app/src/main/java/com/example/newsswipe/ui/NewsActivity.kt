package com.example.newsswipe.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsswipe.R
import com.google.firebase.auth.FirebaseAuth
import com.koushikdutta.ion.Ion
import org.json.JSONArray
import org.json.JSONObject


class NewsActivity : AppCompatActivity() {

    private val link = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=7d127856d20d4cfd830aca5f42dfa305"

    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val settingsButton = findViewById<Button>(R.id.settings_button)
        val searchButton = findViewById<Button>(R.id.search_button)
        val logoutButton = findViewById<Button>(R.id.logout_button)
        val username = findViewById<TextView>(R.id.username)


        //val fragment: Fragment = SwipeFragment.newInstance()

        if (mAuth.currentUser != null){
            username.text = mAuth.currentUser?.email
        }
        else{
            logoutButton.text = getString(R.string.login)
            username.text = getString(R.string.guest)
        }


        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        settingsButton.setOnClickListener {
            //Log.i("Settings", "settings")
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        searchButton.setOnClickListener{
            //search()
            Log.d("API","search")
        }
    }

    fun search(){
        Log.d("API", R.string.API_KEY.toString())
        Ion.with(this)
            .load(link)
            .setHeader("Accept", "application/json")
            .setHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
            .asString()
            .setCallback { _, result ->print(result)
            }
    }

    private fun print(data: String){
        Log.d("API",data)
        val myJSON = JSONObject(data)
        val status = myJSON.getString("status")
        Log.d("API", "status = $status")

        val listArticles = myJSON.getString("articles")
        Log.d("API", listArticles)

        val myJSONArticles = JSONArray(listArticles)

        for (i in 0 until myJSONArticles.length()) {
            Log.d("API",i.toString())
            val row = JSONObject(myJSONArticles.getJSONObject(i).toString())
            val title = row.getString("title")
            val author = row.getString("author")
            val url = row.getString("url")
            val image = row.getString("urlToImage")
            Log.d("API", "title = $title, author = $author, url = $url, image = $image")
        }

        Log.d("API", myJSONArticles.length().toString())
        //Log.d("API", list_articles[1].toString())
    }
}