package com.simform.ssfurnicraftar.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simform.ssfurnicraftar.ui.AboutScreen
import com.simform.ssfurnicraftar.ui.AppState
import com.simform.ssfurnicraftar.ui.SettingsScreen
import com.simform.ssfurnicraftar.ui.ThemeScreen
import com.simform.ssfurnicraftar.ui.favorites.FavoritesRoute
import com.simform.ssfurnicraftar.ui.arview.navigation.arViewScreen
import com.simform.ssfurnicraftar.ui.arview.navigation.navigateToARView
import com.simform.ssfurnicraftar.ui.download.navigation.downloadScreen
import com.simform.ssfurnicraftar.ui.download.navigation.navigateToDownload
import com.simform.ssfurnicraftar.ui.products.navigation.PRODUCTS_ROUTE
import com.simform.ssfurnicraftar.ui.products.navigation.navigateToProducts
import com.simform.ssfurnicraftar.ui.products.navigation.productsScreen
import timber.log.Timber

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: AppState,
    searchQuery: String = "",
    startDestination: String = PRODUCTS_ROUTE,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {

    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        productsScreen(
            onProductClick = { productId ->
                Timber.d("Clicked: $productId")
                navController.navigateToDownload(productId)
            },
            searchQuery = searchQuery
        )

        downloadScreen { productId, _, color ->
            navController.navigateToARView(productId, color)
        }

        arViewScreen(
            onNavigateBack = { navController.navigateToProducts() },
            onShowSnackbar = onShowSnackbar
        )
        
        // Favorites Screen
        composable(
            route = "favorites",
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            FavoritesRoute(
                onProductClick = { productId ->
                    navController.navigateToDownload(productId)
                }
            )
        }
        
        // Settings Screen
        composable(
            route = "settings",
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            SettingsScreen()
        }
        
        // Theme Screen
        composable(
            route = "theme",
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            ThemeScreen()
        }
        
        // About Screen
        composable(
            route = "about",
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) +
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) +
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            }
        ) {
            AboutScreen()
        }
    }
}
