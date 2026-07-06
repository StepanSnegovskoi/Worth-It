package com.metes.worthit.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.metes.worthit.ui.navigation.AppNavigation
import com.metes.worthit.ui.screen.main.ItemsRoute
import com.metes.worthit.ui.theme.WorthItTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorthItTheme {
                val navController = rememberNavController()

                AppNavigation(
                    modifier = Modifier
                        .fillMaxSize(),
                    navHostController = navController
                )
            }
        }
    }
}