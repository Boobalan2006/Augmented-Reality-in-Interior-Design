package com.simform.ssfurnicraftar.data.local.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val productId: String,
    val name: String,
    val thumbnail: String,
    val addedAt: Long = System.currentTimeMillis()
)
