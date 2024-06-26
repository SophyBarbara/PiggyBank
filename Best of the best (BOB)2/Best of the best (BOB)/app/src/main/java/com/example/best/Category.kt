package com.example.best

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "category")
data class Category(
    @PrimaryKey @ColumnInfo(name = "Categoryname")
    var name: String,
    @ColumnInfo(name = "value")
    var value: Double,
)
