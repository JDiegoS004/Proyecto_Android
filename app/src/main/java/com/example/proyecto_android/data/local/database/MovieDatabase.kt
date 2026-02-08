package com.example.proyecto_android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyecto_android.data.local.dao.MovieDao
import com.example.proyecto_android.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 2,   // súbelo 1 número
    exportSchema = false
)


abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

