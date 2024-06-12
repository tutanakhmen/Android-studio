package com.dicoding.UMKMConnect.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.UMKMConnect.data.UmkmRepository
import com.dicoding.UMKMConnect.model.Umkm
import com.dicoding.UMKMConnect.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: UmkmRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Umkm>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Umkm>>
        get() = _uiState

    fun getUmkmById(umkmId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getUmkmById(umkmId))
        }
    }

    fun addUmkmFavorite(id: Long, isFavorite:Boolean) =
        viewModelScope.launch {
            repository.updateUmkm(id, isFavorite)
                .collect{
                    if (it) getUmkmById(id)
                }
        }

}