package com.example.best

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Statistics")
data class Statistics (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "month")
    var month: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "sum")
    var sum: Double,
    @ColumnInfo(name = "year")
    var year: Int
)