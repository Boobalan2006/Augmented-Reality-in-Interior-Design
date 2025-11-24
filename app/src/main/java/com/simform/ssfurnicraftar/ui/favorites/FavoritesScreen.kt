package com.simform.ssfurnicraftar.ui.favorites

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.simform.ssfurnicraftar.R
import com.simform.ssfurnicraftar.data.local.database.model.FavoriteEntity
import com.simform.ssfurnicraftar.ui.theme.LocalDimens

@Composable
fun FavoritesRoute(
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    FavoritesScreen(
        modifier = modifier,
        favorites = favorites,
        onProductClick = onProductClick,
        onRemoveFromFavorites = viewModel::removeFromFavorites
    )
}

@Composable
private fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favorites: List<FavoriteEntity>,
    onProductClick: (String) -> Unit,
    onRemoveFromFavorites: (FavoriteEntity) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (favorites.isEmpty()) {
            NoFavorites(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(horizontal = LocalDimens.SpacingLarge),
                contentPadding = PaddingValues(vertical = LocalDimens.SpacingMedium),
                columns = GridCells.Adaptive(LocalDimens.Products.CardSize),
                verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingLarge),
                horizontalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium)
            ) {
                items(favorites) { favorite ->
                    FavoriteCard(
                        favorite = favorite,
                        onProductClick = onProductClick,
                        onRemoveFromFavorites = onRemoveFromFavorites
                    )
                }
            }
        }
    }
}

@Composable
private fun NoFavorites(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(LocalDimens.IconXXL),
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null
            )

            Text(
                text = "No Favorites Yet",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Text(
                text = "Long press on furniture items to add them to favorites",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FavoriteCard(
    modifier: Modifier = Modifier,
    favorite: FavoriteEntity,
    onProductClick: (String) -> Unit,
    onRemoveFromFavorites: (FavoriteEntity) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 300f),
        label = "FavoriteCardScale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .aspectRatio(ratio = LocalDimens.Products.CardRatio)
            .combinedClickable(
                onClick = { onProductClick(favorite.productId) },
                onLongClick = { onRemoveFromFavorites(favorite) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Box {
            SubcomposeAsyncImage(
                model = favorite.thumbnail,
                contentDescription = favorite.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(LocalDimens.IconLarge)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
            
            // Favorite icon overlay
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(LocalDimens.SpacingSmall)
            ) {
                IconButton(
                    onClick = { onRemoveFromFavorites(favorite) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove from favorites",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            
            // Product name at bottom
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(LocalDimens.SpacingSmall)
            ) {
                Text(
                    text = favorite.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
