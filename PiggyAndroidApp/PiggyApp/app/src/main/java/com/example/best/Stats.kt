package com.example.best

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best.adapter.Lk
import com.example.best.adapter.Statisticsadapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Stats : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        val back = findViewById<TextView>(R.id.tvChartName)
        initial()
        back.setOnClickListener{
            val intent = Intent(this, Lk::class.java)
            this.finishAffinity()
            startActivity(intent)
        }
    }
    private fun initial() {
        recyclerView =findViewById(R.id.recview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val statistics = getStat()
            val adapter = Statisticsadapter(this@Stats, statistics)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getStat(): List<Statistics> = withContext(Dispatchers.IO) {
        val dao = Db.getMyDb(this@Stats).getMyDao()
        dao.getStat()
    }

}