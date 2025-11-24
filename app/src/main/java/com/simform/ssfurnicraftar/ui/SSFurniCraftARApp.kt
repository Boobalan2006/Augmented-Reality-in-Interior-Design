package com.simform.ssfurnicraftar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simform.ssfurnicraftar.R
import com.simform.ssfurnicraftar.navigation.AppNavHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SSFurniCraftARApp(
    appState: AppState
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val snackbarHostState = remember { SnackbarHostState() }

        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        // If user is not connected to the internet show a snack bar to inform them.
        val notConnectedMessage = stringResource(R.string.not_connected)
        LaunchedEffect(key1 = isOffline) {
            if (isOffline) {
                snackbarHostState.showSnackbar(
                    message = notConnectedMessage,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawerContent(
                    onItemClick = { item ->
                        scope.launch {
                            drawerState.close()
                        }
                        // Handle navigation based on item
                        when (item) {
                            "home" -> {
                                // Navigate to products screen (home page)
                                appState.navController.navigate("products_route") {
                                    popUpTo("products_route") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                            "favorites" -> {
                                appState.navController.navigate("favorites")
                            }
                            "settings" -> {
                                appState.navController.navigate("settings")
                            }
                            "theme" -> {
                                appState.navController.navigate("theme")
                            }
                            "about" -> {
                                appState.navController.navigate("about")
                            }
                        }
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    AnimatedTopAppBar(
                        onMenuClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        },
                        searchQuery = searchQuery,
                        isSearchActive = isSearchActive,
                        onSearchClick = {
                            isSearchActive = !isSearchActive
                            if (!isSearchActive) {
                                searchQuery = ""
                            }
                        },
                        onSearchQueryChange = { query ->
                            searchQuery = query
                        }
                    )
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                ) {
                    AppNavHost(
                        appState = appState,
                        searchQuery = searchQuery,
                        onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                withDismissAction = true,
                                duration = SnackbarDuration.Short
                            ) == SnackbarResult.ActionPerformed
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedTopAppBar(
    onMenuClick: () -> Unit = {},
    searchQuery: String = "",
    isSearchActive: Boolean = false,
    onSearchClick: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {}
) {
    // Animation for search bar expansion
    val searchBarAlpha by animateFloatAsState(
        targetValue = if (isSearchActive) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "SearchBarAlpha"
    )
    
    val titleAlpha by animateFloatAsState(
        targetValue = if (isSearchActive) 0f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "TitleAlpha"
    )
    
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title with fade animation
                if (!isSearchActive) {
                    Text(
                        text = "ARID",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.graphicsLayer(alpha = titleAlpha)
                    )
                }
                
                // Animated search bar
                AnimatedVisibility(
                    visible = isSearchActive,
                    enter = fadeIn(animationSpec = tween(300)) + 
                            expandHorizontally(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(200)) + 
                           shrinkHorizontally(animationSpec = tween(200))
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = { 
                            Text(
                                "Search furniture...",
                                style = MaterialTheme.typography.bodyMedium
                            ) 
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .graphicsLayer(alpha = searchBarAlpha),
                        shape = RoundedCornerShape(24.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        } else null
                    )
                }
            }
        },
        navigationIcon = {
            AnimatedIconButton(
                onClick = onMenuClick,
                icon = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        },
        actions = {
            AnimatedIconButton(
                onClick = onSearchClick,
                icon = if (isSearchActive) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = if (isSearchActive) "Close search" else "Search"
            )
            if (!isSearchActive) {
                AnimatedIconButton(
                    onClick = { /* Handle more options click */ },
                    icon = Icons.Default.MoreVert,
                    contentDescription = "More options"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
private fun AnimatedIconButton(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "IconButtonScale"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.scale(scale),
        interactionSource = interactionSource
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun NavigationDrawerContent(
    onItemClick: (String) -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        // App header
        Text(
            text = "ARID",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
        
        HorizontalDivider()
        
        // Navigation items
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = false,
            onClick = { onItemClick("home") },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("Favorites") },
            selected = false,
            onClick = { onItemClick("favorites") },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = false,
            onClick = { onItemClick("settings") },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Palette, contentDescription = null) },
            label = { Text("Theme") },
            selected = false,
            onClick = { onItemClick("theme") },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            label = { Text("About") },
            selected = false,
            onClick = { onItemClick("about") },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}
