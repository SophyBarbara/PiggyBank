package com.example.best

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best.adapter.Categoryadapter
import com.example.best.adapter.Lk
import com.example.best.adapter.Notadapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class notiflist : AppCompatActivity() {
    lateinit var adapter: Categoryadapter

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notiflist)

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
            val adapter = Notadapter(this@notiflist, categories)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getCat(): List<Notifications> = withContext(Dispatchers.IO) {
        val dao = Db.getMyDb(this@notiflist).getMyDao()
        dao.getNotif()
    }
}