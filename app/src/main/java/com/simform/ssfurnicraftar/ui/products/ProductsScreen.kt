package com.simform.ssfurnicraftar.ui.products
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.simform.ssfurnicraftar.R
import com.simform.ssfurnicraftar.data.local.database.model.FavoriteEntity
import com.simform.ssfurnicraftar.data.model.Category
import com.simform.ssfurnicraftar.data.model.Product
import com.simform.ssfurnicraftar.ui.favorites.FavoritesViewModel
import com.simform.ssfurnicraftar.ui.theme.LocalDimens

@Composable
fun ProductsRoute(
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit,
    searchQuery: String = "",
    viewModel: ProductsViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val currentCategory by viewModel.currentCategory.collectAsStateWithLifecycle()
    val productItems = viewModel.modelsFlow.collectAsLazyPagingItems()
    val favorites by favoritesViewModel.favorites.collectAsStateWithLifecycle()

    // Update search query in ViewModel when it changes
    LaunchedEffect(searchQuery) {
        viewModel.updateSearchQuery(searchQuery)
    }

    ProductsScreen(
        modifier = modifier,
        categories = viewModel.categories,
        currentCategory = currentCategory,
        productItems = productItems,
        favorites = favorites,
        onCategoryChange = viewModel::changeCategory,
        onProductClick = onProductClick,
        onAddToFavorites = { product ->
            favoritesViewModel.addToFavorites(
                productId = product.id,
                name = product.name,
                thumbnail = product.thumbnail
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductsScreen(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    currentCategory: Category,
    productItems: LazyPagingItems<Product>,
    favorites: List<FavoriteEntity> = emptyList(),
    onCategoryChange: (Category) -> Unit,
    onProductClick: (String) -> Unit,
    onAddToFavorites: (Product) -> Unit = {}
) {

    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            productItems.refresh()
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        Column {
            CategoryHeader(
                categories = categories,
                currentCategory = currentCategory,
                onCategoryChange = onCategoryChange
            )

            Products(
                productItems = productItems,
                favorites = favorites,
                onProductClick = onProductClick,
                onAddToFavorites = onAddToFavorites
            )
        }

        if (productItems.loadState.refresh !is LoadState.Loading) {
            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState
            )
        }
    }
}

@Composable
private fun CategoryHeader(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    currentCategory: Category,
    onCategoryChange: (Category) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(top = LocalDimens.SpacingSmall, bottom = LocalDimens.SpacingXS),
        contentPadding = PaddingValues(horizontal = LocalDimens.SpacingLarge),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.SpacingSmall)
    ) {
        items(categories) { category ->
            CategoryCard(
                isSelected = currentCategory == category,
                category = category,
                onClick = { onCategoryChange(category) }
            )
        }
    }
}

@Composable
private fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Very pronounced press animation - more visible
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.8f else 1f,
        animationSpec = spring(dampingRatio = 0.3f, stiffness = 600f),
        label = "CategoryChipScale"
    )
    
    // More dramatic selection animation
    val selectedScale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "CategoryChipSelectedScale"
    )
    
    // Add rotation animation for extra visual feedback
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) -2f else 0f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "CategoryChipRotation"
    )
    
    // Add alpha animation
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "CategoryChipAlpha"
    )

    FilterChip(
        selected = isSelected,
        modifier = modifier
            .scale(scale * selectedScale)
            .graphicsLayer(
                rotationZ = rotation,
                alpha = alpha
            ),
        onClick = onClick,
        interactionSource = interactionSource,
        label = {
            Text(
                text = stringResource(category.nameRes),
                style = MaterialTheme.typography.labelMedium
            )
        }
    )
}

@Composable
fun Products(
    modifier: Modifier = Modifier,
    productItems: LazyPagingItems<Product>,
    favorites: List<FavoriteEntity> = emptyList(),
    onProductClick: (String) -> Unit,
    onAddToFavorites: (Product) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        val refreshState = productItems.loadState.refresh
        val sourceState = productItems.loadState.source.refresh
        val mediatorState = productItems.loadState.mediator?.refresh

        when {
            // If there is no data available to display, show no data view.
            // Initially item counts might be zero. We must check that source
            // state or remote mediator is not in loading state.
            productItems.itemCount == 0
                    && sourceState !is LoadState.Loading
                    && mediatorState !is LoadState.Loading -> {
                NoData(modifier = Modifier.align(Alignment.Center))
            }

            // When products are loading show product placeholders.
            // Loading can be either by refresh state (initial load/pull to refresh) or
            // by sourceState when changing the category.
            refreshState is LoadState.Loading
                    || (sourceState is LoadState.Loading && productItems.itemCount == 0) -> {
                ProductsLoading()
            }

            else -> {
                ProductsContent(
                    products = productItems,
                    favorites = favorites,
                    onProductClick = onProductClick,
                    onAddToFavorites = onAddToFavorites
                )
            }
        }
    }
}

@Composable
private fun ProductsLoading(
    modifier: Modifier = Modifier
) {
    ProductsGrid(modifier = modifier) {
        items(LocalDimens.Products.PlaceholdersCount) {
            Box(
                modifier = modifier
                    .aspectRatio(LocalDimens.Products.CardRatio)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
private fun ProductsContent(
    modifier: Modifier = Modifier,
    products: LazyPagingItems<Product>,
    favorites: List<FavoriteEntity> = emptyList(),
    onProductClick: (String) -> Unit,
    onAddToFavorites: (Product) -> Unit = {}
) {
    ProductsGrid(modifier = modifier) {
        items(products.itemCount) { index ->
            products[index]?.let { product ->
                ProductCard(
                    model = product,
                    onProductClick = onProductClick,
                    onLongPress = { onAddToFavorites(product) },
                    isFavorite = favorites.any { it.productId == product.id }
                )
            }
        }
    }
}

@Composable
private fun ProductsGrid(
    modifier: Modifier = Modifier,
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(horizontal = LocalDimens.SpacingLarge),
        contentPadding = PaddingValues(vertical = LocalDimens.SpacingMedium),
        columns = GridCells.Adaptive(LocalDimens.Products.CardSize),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingLarge),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium),
        content = content
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProductCard(
    modifier: Modifier = Modifier,
    model: Product,
    onProductClick: (String) -> Unit,
    onLongPress: (Product) -> Unit = {},
    isFavorite: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 300f),
        label = "ProductCardScale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .aspectRatio(ratio = LocalDimens.Products.CardRatio)
            .combinedClickable(
                onClick = { onProductClick(model.id) },
                onLongClick = { onLongPress(model) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.weight(1f),
            model = model.thumbnail,
            contentDescription = model.name,
            contentScale = ContentScale.FillHeight,
            loading = {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(LocalDimens.SpacingSmall),
                    painter = painterResource(id = R.drawable.ic_model_placeholder),
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(alpha = LocalDimens.Products.PlaceholderAlpha)
                )
            },
            error = {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(LocalDimens.SpacingSmall),
                    imageVector = Icons.Default.BrokenImage,
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(alpha = LocalDimens.Products.PlaceholderAlpha)
                )
            }
        )

        // Favorite icon overlay
        Box {
            Text(
                modifier = Modifier.padding(LocalDimens.SpacingSmall),
                text = model.name,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun NoData(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingSmall)
        ) {
            Icon(
                modifier = modifier.size(LocalDimens.IconXXL),
                imageVector = Icons.AutoMirrored.Default.List,
                contentDescription = null
            )

            Text(
                text = stringResource(R.string.no_data),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

