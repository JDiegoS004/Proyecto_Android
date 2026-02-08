package com.example.proyecto_android.ui.theme.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Esta clase es necesaria para que Hilt pueda generar el código de inyección de dependencias.
// Además, deberemos declararla en el Manifest del programa.
@HiltAndroidApp
class MovieApp : Application()
