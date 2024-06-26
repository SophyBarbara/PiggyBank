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

class Categoryadapter(private var context:Context, var categories:List<Category>): RecyclerView.Adapter<Categoryadapter.CategoryViewHolder>(){



    class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        var categoryname = view.findViewById<TextView>(R.id.categoryname)
        var categoryvalue = view.findViewById<TextView>(R.id.categoryvalue)
        var deletebutton = view.findViewById<Button>(R.id.deletebutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryname.text = categories[position].name
        holder.categoryvalue.text = categories[position].value.toString()
        holder.deletebutton.setOnClickListener {
            val Dao = Db.getMyDb(context).getMyDao()
            Thread{
                Dao.deleteCat(categories[position])
                categories = Dao.getCategoriesEdit()
                (context as Activity).runOnUiThread {
                    notifyDataSetChanged()
                }
            }.start()
        }
    }

}