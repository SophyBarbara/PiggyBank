package com.example.best.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.best.Category
import com.example.best.Category_management
import com.example.best.Db
import com.example.best.R

class Donutadapter(private var context:Context, var categories:List<Category>): RecyclerView.Adapter<Donutadapter.CategoryViewHolder>(){



    class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var categoryline = view.findViewById<TextView>(R.id.categoryline)
        var categoryname = view.findViewById<TextView>(R.id.categoryname)
        var categoryvalue = view.findViewById<TextView>(R.id.categoryvalue)
        var linearlayout = view.findViewById<LinearLayout>(R.id.linearlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donut_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.categoryline.width=((categories[position].value/categories[0].value)*600).toInt() +10
        holder.categoryname.text = categories[position].name
        holder.categoryvalue.text = categories[position].value.toString()
        val Dao = Db.getMyDb(context).getMyDao()
        var rgb=Dao.getColor(position%10)
        holder.categoryline.setBackgroundColor(Color.rgb(rgb.first,rgb.second,rgb.third))
    }

}