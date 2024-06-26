package com.example.best.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.example.best.Statistics

class Statisticsadapter(private var context:Context, var statistics:List<Statistics>): RecyclerView.Adapter<Statisticsadapter.CategoryViewHolder>(){

    class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var categoryline = view.findViewById<TextView>(R.id.categoryline)
        var categoryname = view.findViewById<TextView>(R.id.categoryname)
        var categoryvalue = view.findViewById<TextView>(R.id.categoryvalue)
        var date = view.findViewById<TextView>(R.id.date)
        var linearlayout = view.findViewById<LinearLayout>(R.id.linearlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.statistics_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statistics.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryline.width=((statistics[position].sum/statistics[0].sum)*statistics[position].sum).toInt() +10
        holder.categoryname.text = statistics[position].name
        holder.categoryvalue.text = statistics[position].sum.toString()
        holder.date.text = "${statistics[position].month}.${statistics[position].year}"
        val Dao = Db.getMyDb(context).getMyDao()
        var rgb=Dao.getColor(statistics[position].month)
        holder.linearlayout.setBackgroundColor(Color.rgb(rgb.first,rgb.second,rgb.third))
        holder.categoryline.setBackgroundColor(Color.rgb(47, 80, 59))
    }

}