package com.example.best

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.best.adapter.Categoryadapter
import com.example.best.adapter.Lk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Category_management : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
lateinit var adapter:Categoryadapter

lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category_management)

        val back = findViewById<TextView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, Lk::class.java)
            startActivity(intent)
        }
        initial()

        val add= findViewById<Button>(R.id.add)
        var context: Context = this

        add.setOnClickListener {
            val Dao = Db.getMyDb(this).getMyDao()
            var unique: Int=1
            var namecheck=findViewById<TextView>(R.id.nameText).text.toString()
            Thread {
                unique = Dao.checkIfExists(namecheck)
                runOnUiThread {
                    if (unique == 0) {
//                        namecheck = findViewById<TextView>(R.id.nameText).text.toString()
                        val cat = Category(
                            namecheck,
                            0.0
                        )
                        Thread {
                            Dao.InsertCategory(cat)
                        }.start()
                        val intent = Intent(this, Category_management::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        this.startActivity(intent)
                        (this as Activity).finish()
                    }
                    else {
                        Toast.makeText(
                            context,
                            "Введите уникальное имя для категории",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                }.start()


            }

    }

    private fun initial() {
        recyclerView =findViewById(R.id.recview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            val categories = getCat()
            val adapter = Categoryadapter(this@Category_management, categories)
            recyclerView.adapter = adapter
        }
    }

    private suspend fun getCat(): List<Category> = withContext(Dispatchers.IO) {
        val dao = Db.getMyDb(this@Category_management).getMyDao()
        dao.getCategoriesEdit()
    }
}