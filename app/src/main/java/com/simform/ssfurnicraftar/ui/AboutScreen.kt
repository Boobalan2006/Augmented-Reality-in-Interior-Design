package com.simform.ssfurnicraftar.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simform.ssfurnicraftar.ui.theme.LocalDimens
import kotlinx.coroutines.delay

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    var showIcon by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showButtons by remember { mutableStateOf(false) }
    var showCard1 by remember { mutableStateOf(false) }
    var showCard2 by remember { mutableStateOf(false) }
    var showCard3 by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showIcon = true
        delay(100)
        showTitle = true
        delay(100)
        showButtons = true
        delay(100)
        showCard1 = true
        delay(100)
        showCard2 = true
        delay(100)
        showCard3 = true
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(LocalDimens.SpacingLarge)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingLarge)
    ) {
        // App Icon
        AnimatedVisibility(
            visible = showIcon,
            enter = fadeIn(animationSpec = tween(500)) + 
                    slideInVertically(animationSpec = tween(500)) { -it }
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // App Name and Version
        AnimatedVisibility(
            visible = showTitle,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { -it / 2 }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingSmall)
            ) {
                Text(
                    text = "ARID",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Share and Rate Buttons
        AnimatedVisibility(
            visible = showButtons,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium)
            ) {
                // Share App Button
                AnimatedButton(
                    onClick = { shareApp(context) },
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Share,
                    text = "Share App"
                )
                
                // Rate App Button
                AnimatedButton(
                    onClick = { rateApp(context) },
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Star,
                    text = "Rate App",
                    isPrimary = true
                )
            }
        }

        // Description Card
        AnimatedVisibility(
            visible = showCard1,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            Card {
            Column(
                modifier = Modifier.padding(LocalDimens.SpacingLarge),
                verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium)
            ) {
                Text(
                    text = "About ARID",
                    style = MaterialTheme.typography.titleLarge
                )
                
                Text(
                    text = "ARID is an innovative AR furniture visualization app that lets you place and view 3D furniture models in your real space using augmented reality technology.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }

        // Features Card
        AnimatedVisibility(
            visible = showCard2,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            Card {
                Column(
                    modifier = Modifier.padding(LocalDimens.SpacingLarge),
                    verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium)
                ) {
                    Text(
                        text = "Features",
                        style = MaterialTheme.typography.titleLarge
                    )
                    
                    val features = listOf(
                        "🪑 Browse furniture categories (Tables, Chairs, Beds, Sofas, Desks, Curtains)",
                        "❤️ Save favorites with long press",
                        "🔍 Search furniture items",
                        "🎨 Customize colors and materials",
                        "📱 AR visualization in your space",
                        "🎯 Precise placement and scaling",
                        "📤 Share your AR setups"
                    )
                    
                    features.forEach { feature ->
                        Text(
                            text = feature,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Developer Info Card
        AnimatedVisibility(
            visible = showCard3,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            Card {
            Column(
                modifier = Modifier.padding(LocalDimens.SpacingLarge),
                verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium)
            ) {
                Text(
                    text = "Developer",
                    style = MaterialTheme.typography.titleLarge
                )
                
                Text(
                    text = "Built with ❤️ using Jetpack Compose, ARCore, and modern Android development practices.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
                
                Text(
                    text = "© 2024 ARID Team",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(LocalDimens.SpacingLarge))
            }
        }
    }
}

@Composable
private fun AnimatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    isPrimary: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "ButtonScale"
    )
    
    if (isPrimary) {
        Button(
            onClick = onClick,
            modifier = modifier.scale(scale),
            interactionSource = interactionSource
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = text)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.scale(scale),
            interactionSource = interactionSource
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = text)
        }
    }
}

private fun shareApp(context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check out ARID - AR Furniture App")
        putExtra(
            Intent.EXTRA_TEXT,
            "Check out ARID, an amazing AR furniture visualization app! " +
            "Download it from: https://play.google.com/store/apps/details?id=${context.packageName}"
        )
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share ARID via"))
}

private fun rateApp(context: Context) {
    try {
        // Try to open Play Store app
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${context.packageName}"))
        context.startActivity(intent)
    } catch (e: Exception) {
        // If Play Store app is not available, open in browser
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
        )
        context.startActivity(intent)
    }
}
