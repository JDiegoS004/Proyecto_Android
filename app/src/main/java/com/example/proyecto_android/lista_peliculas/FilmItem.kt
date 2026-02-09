package com.example.proyecto_android.lista_peliculas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyecto_android.data.local.entity.MovieEntity

@Composable
fun FilmItem(
    movie: MovieEntity,
    onDeleteClick: (MovieEntity) -> Unit,
    onEditClick: (MovieEntity, String) -> Unit,
    onClick: () -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf(movie.nota?:"") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = movie.poster_path,
                    contentDescription = movie.title,
                    modifier = Modifier.size(60.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = movie.release_date,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(onClick = { isEditing = !isEditing }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }

                IconButton(onClick = { onDeleteClick(movie) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }

            if (isEditing) {
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Personal notes") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onEditClick(movie, noteText)
                        isEditing = false
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
