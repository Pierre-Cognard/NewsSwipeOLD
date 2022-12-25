package com.example.newsswipe.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsswipe.R
import com.google.firebase.auth.FirebaseAuth

class KeywordAdapter(private val list: List<String>) : RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.keyword_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list[position]
        holder.keyword.text = info

        holder.deleteButton.setOnClickListener{
            Log.i("Click", position.toString())


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
