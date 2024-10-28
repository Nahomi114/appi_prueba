package com.example.appi.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appi.api.ApiService
import com.example.appi.model.NutritionItem
import kotlinx.coroutines.launch

class NutritionViewModel : ViewModel() {
    private val _nutritionInfo = MutableLiveData<List<NutritionItem>>()
    val nutritionInfo: LiveData<List<NutritionItem>> = _nutritionInfo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getNutritionInfo(query: String) {
        viewModelScope.launch {
            try {
                val response = ApiService.api.getNutrition(query)

                if (response.isSuccessful) {
                    response.body()?.let { nutritionResponse ->
                        _nutritionInfo.value = nutritionResponse.items
                    } ?: run {
                        _error.value = "Respuesta vacía"
                    }
                } else {
                    _error.value = "Error: ${response.code()} - ${response.message()}"
                    Log.e("NutritionViewModel", "HTTP error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                Log.e("NutritionViewModel", "Error fetching nutrition info", e)
                e.printStackTrace()
            }
        }
    }
}
