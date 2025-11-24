package com.simform.ssfurnicraftar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.simform.ssfurnicraftar.ui.theme.LocalDimens
import kotlinx.coroutines.delay

@Composable
fun ThemeScreen(
    modifier: Modifier = Modifier,
    viewModel: ThemeViewModel = hiltViewModel()
) {
    val selectedTheme by viewModel.themeMode.collectAsStateWithLifecycle()
    
    var showTitle by remember { mutableStateOf(false) }
    var showSubtitle by remember { mutableStateOf(false) }
    var showCard1 by remember { mutableStateOf(false) }
    var showCard2 by remember { mutableStateOf(false) }
    var showCard3 by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showTitle = true
        delay(100)
        showSubtitle = true
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
            .padding(LocalDimens.SpacingLarge),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium)
    ) {
        AnimatedVisibility(
            visible = showTitle,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { -it }
        ) {
            Text(
                text = "Theme",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = LocalDimens.SpacingMedium)
            )
        }

        AnimatedVisibility(
            visible = showSubtitle,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { -it / 2 }
        ) {
            Text(
                text = "Choose your preferred theme",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = LocalDimens.SpacingSmall)
            )
        }

        // Light Theme Option
        AnimatedVisibility(
            visible = showCard1,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            ThemeOptionCard(
                title = "Light",
                description = "Light theme for daytime use",
                icon = Icons.Default.LightMode,
                isSelected = selectedTheme == ThemeMode.LIGHT,
                onClick = { viewModel.setThemeMode(ThemeMode.LIGHT) }
            )
        }

        // Dark Theme Option
        AnimatedVisibility(
            visible = showCard2,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            ThemeOptionCard(
                title = "Dark",
                description = "Dark theme for nighttime use",
                icon = Icons.Default.DarkMode,
                isSelected = selectedTheme == ThemeMode.DARK,
                onClick = { viewModel.setThemeMode(ThemeMode.DARK) }
            )
        }

        // System Theme Option
        AnimatedVisibility(
            visible = showCard3,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            ThemeOptionCard(
                title = "System Default",
                description = "Follow system theme settings",
                icon = Icons.Default.Smartphone,
                isSelected = selectedTheme == ThemeMode.SYSTEM,
                onClick = { viewModel.setThemeMode(ThemeMode.SYSTEM) }
            )
        }
    }
}

@Composable
private fun ThemeOptionCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalDimens.SpacingLarge),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(LocalDimens.SpacingMedium),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSelected) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            RadioButton(
                selected = isSelected,
                onClick = onClick
            )
        }
    }
}
