package com.simform.ssfurnicraftar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simform.ssfurnicraftar.ui.theme.LocalDimens
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    var notificationsEnabled by rememberSaveable { mutableStateOf(true) }
    var animationsEnabled by rememberSaveable { mutableStateOf(true) }
    
    var showTitle by remember { mutableStateOf(false) }
    var showCard1 by remember { mutableStateOf(false) }
    var showCard2 by remember { mutableStateOf(false) }
    var showCard3 by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showTitle = true
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
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = LocalDimens.SpacingMedium)
            )
        }

        // Theme Setting
        AnimatedVisibility(
            visible = showCard1,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            SettingCard(
                title = "Dark Theme",
                description = "Switch between light and dark themes",
                icon = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = it }
            )
        }

        // Notifications Setting
        AnimatedVisibility(
            visible = showCard2,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            SettingCard(
                title = "Notifications",
                description = "Enable push notifications",
                icon = Icons.Default.Notifications,
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
        }

        // Animations Setting
        AnimatedVisibility(
            visible = showCard3,
            enter = fadeIn(animationSpec = tween(400)) + 
                    slideInVertically(animationSpec = tween(400)) { it / 2 }
        ) {
            SettingCard(
                title = "Animations",
                description = "Enable UI animations and transitions",
                icon = Icons.Default.Palette,
                checked = animationsEnabled,
                onCheckedChange = { animationsEnabled = it }
            )
        }
    }
}

@Composable
private fun SettingCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
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
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}
