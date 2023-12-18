package com.example.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel:  ViewModel(){

    private val  _categorieState = mutableStateOf(RecipeState())
    // i got an error when i wrote it like categoriesState =  State<RecipeState>
    val categoriesState :  State<RecipeState> = _categorieState

    init {
        fetchCategories()
    }

    private fun  fetchCategories(){
viewModelScope.launch{
try{
val response = recipeService.getCategories()
    _categorieState.value =  _categorieState.value.copy(
        list = response.categories, // Update the list
        loading = false, // Reset the loading
        error = null // Reset the error
    )
} catch (e: Exception) {
    _categorieState.value = _categorieState.value.copy(
        loading = false, // Reset the loading
        error = "Error fetching Categories ${e.message}" // Update the error
    )
}
}
    }



data class RecipeState(
    val loading :   Boolean = true,
    val list : List<Category> = emptyList(),
    // string is nullable and can be null
    val error : String? = null

)
}