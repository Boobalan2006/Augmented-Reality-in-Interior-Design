package com.simform.ssfurnicraftar.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simform.ssfurnicraftar.data.local.database.dao.FavoriteDao
import com.simform.ssfurnicraftar.data.local.database.model.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteDao: FavoriteDao
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteEntity>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            try {
                favoriteDao.getAllFavorites().collect { favoritesList ->
                    _favorites.value = favoritesList
                }
            } catch (e: Exception) {
                // Handle database errors gracefully
                _favorites.value = emptyList()
            }
        }
    }

    fun addToFavorites(productId: String, name: String, thumbnail: String) {
        viewModelScope.launch {
            try {
                val favorite = FavoriteEntity(
                    productId = productId,
                    name = name,
                    thumbnail = thumbnail
                )
                favoriteDao.addToFavorites(favorite)
            } catch (e: Exception) {
                // Handle database errors gracefully
            }
        }
    }

    fun removeFromFavorites(favorite: FavoriteEntity) {
        viewModelScope.launch {
            try {
                favoriteDao.deleteFavorite(favorite)
            } catch (e: Exception) {
                // Handle database errors gracefully
            }
        }
    }

    fun removeFromFavorites(productId: String) {
        viewModelScope.launch {
            try {
                favoriteDao.removeFromFavorites(productId)
            } catch (e: Exception) {
                // Handle database errors gracefully
            }
        }
    }

    suspend fun isFavorite(productId: String): Boolean {
        return try {
            favoriteDao.isFavorite(productId)
        } catch (e: Exception) {
            false
        }
    }
}
