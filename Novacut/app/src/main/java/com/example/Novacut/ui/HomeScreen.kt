package com.example.Novacut.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.Novacut.ui.SlidingSpinningSquircles

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf(HomeTab.EDITS) }
    val context = LocalContext.current

    Box(modifier = Modifier 
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Green, Color.Grey)
            ) 
        )
    ) {
        GreenBackground()
        SlidingSpinningSquircles()
        AnimatedIntroSection()
        HomeTab()
        
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            when (selectedTab) {
                HomeTab.EDITS -> EditsTabContent(
                    projects = projects,
                    onCreateProject = { videoPickerLauncher.launch("video/*") }
                )
                HomeTab.IMAGE -> { /* Image tab content */ }
                HomeTab.AUDIO -> { /* Audio tab content */ }
            }
        }
    }
}
