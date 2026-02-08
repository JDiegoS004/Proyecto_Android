package com.example.proyecto_android.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyecto_android.data.local.dao.MovieDao
import com.example.proyecto_android.data.local.entity.MovieEntity
//Clase que representa la base de datos
@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)

abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

