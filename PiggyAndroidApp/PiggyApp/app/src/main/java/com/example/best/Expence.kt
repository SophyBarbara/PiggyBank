package com.example.best

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.best.adapter.Lk
import java.util.Date

class Expence : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expence)

        val back = findViewById<TextView>(R.id.tvChartName)
        back.setOnClickListener {
            val intent = Intent(this, Lk::class.java)
            startActivity(intent)
        }
        val catspin = findViewById<Spinner>(R.id.catspin)
        val typespin = findViewById<Spinner>(R.id.typespin)
        var sign: Int = 1
        val button = findViewById<Button>(R.id.button)
        val deposit = findViewById<EditText>(R.id.depositEditText)
        val com = findViewById<EditText>(R.id.com)
        Thread {
            val Dao = Db.getMyDb(this).getMyDao()
            val cats = Dao.getCategoriesFullName()
            val catsadapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cats)

            runOnUiThread {
                catspin.adapter = catsadapter
            }

        }.start()
        catspin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val types: Array<String> = when (catspin.selectedItem) {
                    "Сбережения" -> arrayOf("Пополнение сбережений")
                    "Поступления" -> arrayOf(
                        "Пополнение кредитной карты со счета",
                        "Пополнение счета",
                        "Пополнение кредитной карты наличными"
                    )

                    else -> arrayOf("Покупка по дебетовой карте", "Покупка по кредитной карте")
                }
                val typesadapter = ArrayAdapter<String>(
                    this@Expence,
                    android.R.layout.simple_spinner_dropdown_item,
                    types
                )

                typespin.adapter = typesadapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@Expence, "Выберите категорию", Toast.LENGTH_LONG).show()
            }

        }

        typespin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (typespin.selectedItem != null) {
                    val selectedItem = typespin.selectedItem.toString()
                    if (selectedItem == "Пополнение дебетовой карты" || selectedItem == "Пополнение сбережений" || selectedItem == "Покупка по кредитной карте")
                        sign = 1
                    else
                        sign = -1
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@Expence, "Выберите тип операции", Toast.LENGTH_LONG).show()
            }

        }

        button.setOnClickListener {
            if (catspin.selectedItem == null || typespin.selectedItem == null || deposit.text.toString()
                    .equals("") || deposit.text.toString().equals(".")
            ) {
                Toast.makeText(this@Expence, "Заполните обязательные поля", Toast.LENGTH_LONG)
                    .show()

            } else {
                val sum = deposit.text.toString().toDouble()
                val timestamp: Long = System.currentTimeMillis()
                val trans = Transactions(
                    timestamp,
                    catspin.selectedItem.toString(),
                    sum,
                    typespin.selectedItem.toString(),
                    com.text.toString()
                )
                Thread {
                    val Dao = Db.getMyDb(this).getMyDao()

                    val check = Dao.getValue("Остаток по счету")

                    when (catspin.selectedItem.toString()) {
                        "Сбережения" -> {
                            if (check >= sum) {
                                Dao.updateCatTrans("Остаток по счету", sum * -1)
                                Dao.updateCatTrans("Сбережения", sum)
                                Dao.insertTrans(trans)
                                runOnUiThread {
                                    Toast.makeText(
                                        this@Expence,
                                        "Успешно!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                runOnUiThread {
                                    Toast.makeText(
                                        this@Expence,
                                        "Недостаточно средств на счете!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }

                        "Поступления" -> {
                            when (typespin.selectedItem.toString()) {
                                "Пополнение кредитной карты со счета" -> {
                                    if (check >= sum) {
                                        Dao.updateCatTrans("Остаток по счету", sum * -1)
                                        Dao.updateCatTrans("Кредитная карта", sum)
                                        Dao.insertTrans(trans)
                                        runOnUiThread {
                                            Toast.makeText(
                                                this@Expence,
                                                "Успешно!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        runOnUiThread {
                                            Toast.makeText(
                                                this@Expence,
                                                "Недостаточно средств на счете!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }

                                "Пополнение счета" -> {
                                    Dao.updateCatTrans("Остаток по счету", sum)
                                    Dao.updateCatTrans("Поступления", sum)
                                    Dao.insertTrans(trans)
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@Expence,
                                            "Успешно!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                else -> {
                                    Dao.updateCatTrans("Кредитная карта", sum)
                                    Dao.updateCatTrans("Поступления", sum)
                                    Dao.insertTrans(trans)
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@Expence,
                                            "Успешно!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }

                        else -> {
                            when (typespin.selectedItem.toString()) {
                                "Покупка по дебетовой карте" -> {
                                    if (check >= sum) {
                                        Dao.updateCatTrans("Остаток по счету", sum * -1)
                                        Dao.updateCatTrans(catspin.selectedItem.toString(), sum)
                                        Dao.insertTrans(trans)
                                        runOnUiThread {
                                            Toast.makeText(
                                                this@Expence,
                                                "Успешно!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    } else {
                                        runOnUiThread {
                                            Toast.makeText(
                                                this@Expence,
                                                "Недостаточно средств на счете!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }

                                else -> {
                                    Dao.updateCatTrans("Кредитная карта", sum * -1)
                                    Dao.updateCatTrans(catspin.selectedItem.toString(), sum)
                                    Dao.insertTrans(trans)
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@Expence,
                                            "Успешно!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                }.start()
            }
        }
    }
}
