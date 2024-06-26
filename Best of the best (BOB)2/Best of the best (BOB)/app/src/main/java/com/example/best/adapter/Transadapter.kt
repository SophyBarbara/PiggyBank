package com.example.best.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.best.Category
import com.example.best.Category_management
import com.example.best.Db
import com.example.best.R
import com.example.best.Transactions
import java.util.Date

class Transadapter(private var context:Context, var categories:List<Transactions>): RecyclerView.Adapter<Transadapter.CategoryViewHolder>(){



    class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var categoryname = view.findViewById<TextView>(R.id.categoryname)
        var categoryvalue = view.findViewById<TextView>(R.id.categoryvalue)
        var time = view.findViewById<TextView>(R.id.date)
        var com = view.findViewById<TextView>(R.id.com)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transactionslist, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryname.text = categories[position].name
        holder.categoryvalue.text = categories[position].sum.toString()
        var timestamp = categories[position].date
        var date = timestamp?.let { Date(it) }
        holder.time.text = date.toString()
        holder.com.text = categories[position].comm

    }

}