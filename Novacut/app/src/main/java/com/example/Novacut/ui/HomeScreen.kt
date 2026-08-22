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
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf(HomeTab.EDITS) }
    val context = LocalContext.current

    Box(modifier = Modifier 
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Green, Color.Gray)
            ) 
        )
    ) {
        SlidingSpinningSquircles()
        AnimatedIntroSection()
        HomeTabRow()
        
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    }
}
