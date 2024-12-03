package com.example.reservaplusapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.reservaplusapp.R

@Composable
fun SearchContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            onSearch = { /* Implementar búsqueda */ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("Lugar 1", "Lugar 2", "Lugar 3")) { place ->
                SearchResultCard(place)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        modifier = modifier,
        placeholder = { Text("Buscar lugares...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF57BDD3),
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun SearchResultCard(place: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = place,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = place,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Descripción breve del lugar",
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFF57BDD3),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "4.5",
                        color = Color(0xFF57BDD3),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Button(
                onClick = { /* Implementar reserva */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF57BDD3)
                ),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Reservar")
            }
        }
    }
}