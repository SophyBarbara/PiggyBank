package com.example.best

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best.adapter.Categoryadapter
import com.example.best.adapter.Lk
import com.example.best.adapter.Transadapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransList : AppCompatActivity() {
    lateinit var adapter: Transadapter

    lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_list)

        val back = findViewById<TextView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, Lk::class.java)
            startActivity(intent)
        }
        initial()
    }
    private fun initial() {
        recyclerView =findViewById(R.id.recview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val categories = getCat()
            val adapter = Transadapter(this@TransList, categories)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getCat(): List<Transactions> = withContext(Dispatchers.IO) {
        val dao = Db.getMyDb(this@TransList).getMyDao()
        dao.getTrans()
    }
}