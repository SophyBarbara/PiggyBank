package com.example.best

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db.williamchart.view.DonutChartView
import com.example.best.adapter.Categoryadapter
import com.example.best.adapter.Donutadapter
import com.example.best.adapter.Lk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.abs

class Second : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val Dao = Db.getMyDb(this).getMyDao()

        val back = findViewById<TextView>(R.id.tvChartName)
        back.setOnClickListener {
            val intent = Intent(this, Lk::class.java)
            startActivity(intent)
        }
        initial()
        val stats = findViewById<TextView>(R.id.stats)
        stats.setOnClickListener {
            val intent = Intent(this, TransList::class.java)
            startActivity(intent)
        }
    }
    private fun initial() {
        recyclerView =findViewById(R.id.recview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val categories = getCat()
            val adapter = Donutadapter(this@Second, categories)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getCat(): List<Category> = withContext(Dispatchers.IO) {
        val dao = Db.getMyDb(this@Second).getMyDao()
        dao.getCategories()
    }

}