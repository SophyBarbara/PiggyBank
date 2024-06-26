package com.example.best.adapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.best.Category_management
import com.example.best.Db
import com.example.best.Expence
import com.example.best.NotificationsDobav
import com.example.best.R
import com.example.best.Second
import com.example.best.Statistics
import com.example.best.Stats
import java.util.Calendar

class Lk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lk)
        val spendsum = findViewById<TextView>(R.id.spendsum)
        val positivesum = findViewById<TextView>(R.id.positivesum)
        val incomesum = findViewById<TextView>(R.id.incomesum)
        val minusvalue = findViewById<TextView>(R.id.minusvalue)
        val popularname = findViewById<TextView>(R.id.popularname)
        val savedvalue = findViewById<TextView>(R.id.savedvalue)
        val enterexpence = findViewById<TextView>(R.id.enterexpence)
        val view = findViewById<TextView>(R.id.view)
        val viewstat = findViewById<TextView>(R.id.viewstat)
        val viewcat = findViewById<TextView>(R.id.viewcat)
        val viewstatyear = findViewById<TextView>(R.id.viewstatyear)

        Thread {
            val Dao = Db.getMyDb(this).getMyDao()

            val month = Dao.getMonth()
            val year = Dao.getYear()
            val calendar = Calendar.getInstance()
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentyear = calendar.get(Calendar.YEAR) + 1
            if(month!=currentMonth)
            {
                val categories = Dao.getCategoriesFull()
                categories.forEach { item ->
                    if(Dao.annualCheck(item.name, month,year)==0)
                    {
                        val stat = Statistics(
                            null,
                            month,
                            item.name,
                            item.value,
                            year
                        )
                        Dao.InsertStat(stat)
                        Dao.updateCat(item.name,0.0)
                    }
                }
                Dao.updateUser(currentMonth,currentyear)
            }

            var spsum = Dao.getSum()
            var psum =Dao.getValue("Остаток по счету")
            var isum = Dao.getValue("Поступления")
            var mval=Dao.getValue("Кредитная карта")
            var sval=Dao.getValue("Сбережения")
            var popname = Dao.getMax()

            runOnUiThread {


                spendsum.text=spsum.toString()
                positivesum.text=psum.toString()
                incomesum.text=isum.toString()
                minusvalue.text=mval.toString()
                savedvalue.text=sval.toString()
                if(!popname.isNullOrEmpty()) {
                    popularname.text = popname
                }
                else {
                    popularname.text = "Нет трат"
                }
            }

        }.start()

        viewstat.setOnClickListener{
            val intent = Intent(this, Second::class.java)
            startActivity(intent)
        }
        enterexpence.setOnClickListener{
            val intent = Intent(this, Expence::class.java)
            startActivity(intent)
        }
        viewcat.setOnClickListener{
            val intent = Intent(this, Category_management::class.java)
            startActivity(intent)
        }
        viewstatyear.setOnClickListener{
            val intent = Intent(this, Stats::class.java)
            startActivity(intent)
        }
        view.setOnClickListener{
            val intent = Intent(this, NotificationsDobav::class.java)
            startActivity(intent)
        }
    }
}