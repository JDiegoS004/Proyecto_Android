package com.example.proyecto_android.ui.theme.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyecto_android.navigation.NavigationController
import dagger.hilt.android.AndroidEntryPoint

//Clase Main donde se llama a la función NavigationController
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationController()
        }
    }
}


