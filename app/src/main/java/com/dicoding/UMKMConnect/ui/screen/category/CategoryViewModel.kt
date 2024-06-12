package com.dicoding.UMKMConnect.ui.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.UMKMConnect.data.UmkmRepository
import com.dicoding.UMKMConnect.model.Umkm
import com.dicoding.UMKMConnect.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: UmkmRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Umkm>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Umkm>>>
        get() = _uiState

    fun getUmkmByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAllUmkmByCategory(category)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { umkm ->
                    _uiState.value = UiState.Success(umkm)
                }
        }
    }
}