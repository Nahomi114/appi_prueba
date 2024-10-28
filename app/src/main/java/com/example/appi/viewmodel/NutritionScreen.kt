// NutritionScreen.kt
package com.example.appi

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appi.model.NutritionItem
import com.example.appi.viewmodel.NutritionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen(viewModel: NutritionViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val nutritionInfo by viewModel.nutritionInfo.observeAsState(emptyList())
    val error by viewModel.error.observeAsState()
    val isLoading by remember { mutableStateOf(false) } // Estado de carga
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Ingrese alimentos (ej: 1 apple, 2 bananas)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (query.isNotEmpty() && !isLoading) { // Verifica que no esté cargando
                    viewModel.getNutritionInfo(query)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Desactiva el botón mientras carga
        ) {
            Text(if (isLoading) "Cargando..." else "Buscar") // Cambia el texto del botón
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            nutritionInfo.forEach { item ->
                NutritionItemView(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun NutritionItemView(item: NutritionItem) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Nombre: ${item.name}", fontWeight = FontWeight.Bold)
        Text("Calorías: ${item.calories}")
        Text("Proteínas: ${item.protein_g}g")
        Text("Carbohidratos: ${item.carbohydrates_total_g}g")
        Text("Grasas: ${item.fat_total_g}g")
    }
}
