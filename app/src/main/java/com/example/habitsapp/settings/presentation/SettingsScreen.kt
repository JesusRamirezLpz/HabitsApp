package com.example.habitsapp.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitsapp.settings.presentation.components.SettingsItem

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, onLogout: () -> Unit, onBack: () -> Unit) {
    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.primary),
        topBar = { SettingsScreenTopBar { onBack() } }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Text(
                "Support",
                modifier = modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            SettingsItem(
                title = "Contact",
                icon = Icons.Default.Whatsapp,
                onClick = { /*TODO*/ }
            )
            SettingsItem(
                title = "Log out",
                icon = Icons.Default.Info,
                onClick = { onLogout() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenTopBar(onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Settings") },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Settings")
            }
        }
    )
}