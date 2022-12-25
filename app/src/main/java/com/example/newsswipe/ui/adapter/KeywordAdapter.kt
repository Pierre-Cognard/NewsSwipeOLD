package com.example.newsswipe.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsswipe.R
import com.example.newsswipe.database.SqliteDatabase
import com.google.firebase.auth.FirebaseAuth

class KeywordAdapter(private val list: List<String>,private val database: SqliteDatabase, private val context: Context) : RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.keyword_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keyword = list[position]
        holder.keyword.text = keyword

        holder.deleteButton.setOnClickListener{
            val user = if(mAuth.currentUser != null){mAuth.currentUser?.email} else{"guest"}
            val check = database.deleteKeyword(keyword,user.toString())

            if (check == 1)Toast.makeText(context, context.getString(R.string.keyword_delete_success), Toast.LENGTH_SHORT).show()
            else Toast.makeText(context, context.getString(R.string.keyword_delete_error), Toast.LENGTH_SHORT).show()

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var keyword = itemView.findViewById<View>(R.id.keyword_text) as TextView
        var deleteButton = itemView.findViewById<View>(R.id.delete) as ImageView


        override fun onClick(v: View?) {

        }

    }

}

