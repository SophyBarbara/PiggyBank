package com.example.best

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT name FROM user")
    fun getUserName(): String

    @Insert
    fun InsertCategory(category:Category)

    @Insert
    fun InsertStat(stat:Statistics)

    @Insert
    fun InsertNot(not:Notifications)

    @Insert
    fun insertTrans(trans:Transactions)

    @Query("Select * from notifications")
    fun getNotif():List<Notifications>

    @Query("UPDATE category SET value = value + :value WHERE Categoryname = :name")
    fun updateCatTrans(name: String, value: Double)

    @Query("SELECT * FROM Trans")
    fun getTrans(): List<Transactions>

    @Query("SELECT * FROM category Where Categoryname!='Поступления' ORDER BY value DESC")
    fun getCategories(): List<Category>

    @Query("SELECT * FROM category Where Categoryname!='Остаток по счету'")
    fun getCategoriesFull(): List<Category>

    @Query("SELECT Categoryname FROM category Where Categoryname!='Остаток по счету' AND Categoryname!='Кредитная карта'")
    fun getCategoriesFullName(): List<String>

    @Query("SELECT * FROM Statistics ORDER BY sum DESC")
    fun getStat(): List<Statistics>

    @Query("SELECT * FROM category Where Categoryname!='Остаток по счету' AND Categoryname!='Кредитная карта' AND Categoryname!='Сбережения' AND Categoryname!='Поступления' ORDER BY value DESC ")
    fun getCategoriesEdit(): List<Category>

    @Query("SELECT value FROM category")
    fun getCatValues(): List<Double>

    @Delete
    fun deleteCat(category:Category)

    @Delete
    fun deleteNotif(category:Notifications)

    @Query("SELECT COUNT(*) from category WHERE Categoryname = :name")
    fun checkIfExists(name: String): Int

    @Query("SELECT COUNT(*) from Statistics WHERE name =:name AND month =:month AND year=:year")
    fun annualCheck(name: String, month:Int, year:Int): Int

    @Query("SELECT Categoryname FROM category WHERE Categoryname NOT IN ('Остаток по счету', 'Кредитная карта', 'Сбережения', 'Поступления')  Order by value Desc LIMIT 1 ")
    fun getMax(): String

    @Query("Select SUM(value) from Category Where Categoryname NOT IN ('Остаток по счету', 'Кредитная карта', 'Сбережения', 'Поступления')")
    fun getSum():Double

    @Query("SELECT value from category WHERE Categoryname=:name")
    fun getValue(name: String): Double

    @Query("SELECT month from user ")
    fun getMonth(): Int

    @Query("SELECT year from user ")
    fun getYear(): Int

    @Query("UPDATE category SET value = :value WHERE Categoryname = :name")
    fun updateCat(name: String, value: Double)

    @Query("UPDATE user SET month = :month, year = :year")
    fun updateUser(month: Int, year:Int)

    fun getColor(i:Int):Triple<Int,Int,Int> {
        var colorOne:Int=0;
        var colorTwo:Int=0;
        var colorThree:Int=0;
        colorOne = when (i) {
            0 -> 247
            1 -> 224
            2 -> 197
            3 -> 182
            4 -> 151
            5 -> 124
            6 -> 110
            7 -> 79
            8 -> 83
            9 -> 71

            else -> 71
        }
        colorTwo = when (i) {
            0 -> 254
            1 -> 255
            2 ->255
            3 -> 255
            4 -> 255
            5 -> 255
            6 -> 227
            7 -> 218
            8 -> 211
            9 -> 188
            else -> 188
        }
        colorThree = when (i) {
            1 -> 0
            2 -> 0
            3 -> 0
            4 -> 0
            5 -> 0
            6 -> 0
            7 -> 0
            8 -> 0
            9 -> 0
            0 -> 0
            else -> 0
        }
        return Triple(colorOne, colorTwo, colorThree)
    }
}