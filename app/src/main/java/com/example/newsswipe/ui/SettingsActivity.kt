package com.example.newsswipe.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsswipe.R
import com.example.newsswipe.database.SqliteDatabase
import com.example.newsswipe.ui.adapter.KeywordAdapter
import com.google.firebase.auth.FirebaseAuth


class SettingsActivity : AppCompatActivity() {

    private val list = ArrayList<String>()
    private val mDatabase = SqliteDatabase(this)
    private val mAuth = FirebaseAuth.getInstance()

    private val user = if(mAuth.currentUser != null){mAuth.currentUser?.email} else{"guest"}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val appLanguageButton = findViewById<Button>(R.id.app_language_button)
        val newsLanguageButton = findViewById<Button>(R.id.news_language_button)
        val addKeywordButton = findViewById<Button>(R.id.add_keyword_button)
        val backButton = findViewById<Button>(R.id.back_button)
        val keyword = findViewById<TextView>(R.id.keyword)

        val recyclerView = findViewById<View>(R.id.keyword_recycler_view) as RecyclerView
        //val layoutManager = LinearLayoutManager(this)
        //recyclerView.layoutManager = layoutManager
        val mAdapter = KeywordAdapter(list,mDatabase,this)
        recyclerView.adapter = mAdapter


        addKeywordButton.setOnClickListener {
            list.drop(1)
            mAdapter.notifyItemRemoved(2)
            mAdapter.notifyItemRangeChanged(2, list.size)
            if (keyword.text.toString() == "") {
                Toast.makeText(this, getString(R.string.error_empty_keyword), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val check = mDatabase.addKeyword(keyword.text.toString(), user.toString())

                if (check.toInt() == -1){
                    Toast.makeText(this, getString(R.string.keyword_add_error), Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, getString(R.string.keyword_add_success), Toast.LENGTH_SHORT).show()
                    updateKeywordsList()
                }
                hideKeyboard()
            }
            keyword.text = ""
            Log.i("Keyword", list.toString())
        }

        backButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        appLanguageButton.setOnClickListener {
            Log.i("Settings", "App Language")
        }

        newsLanguageButton.setOnClickListener {
            Log.i("Settings", "News Language")
        }
        updateKeywordsList()
    }


    private fun updateKeywordsList(){

        val recyclerView = findViewById<View>(R.id.keyword_recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        list.clear()
        val user = if(mAuth.currentUser != null){mAuth.currentUser?.email} else{"guest"}
        for (elem in mDatabase.findKeywords(user.toString())) list.add(elem)

        val mAdapter = KeywordAdapter(list,mDatabase,this)
        recyclerView.adapter = mAdapter

    }

    private fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}