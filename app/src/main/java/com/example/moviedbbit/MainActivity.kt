package com.example.moviedbbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviedbbit.presentation.navigation.BottomNavItem
import com.example.moviedbbit.presentation.navigation.MovieNavGraph
import com.example.moviedbbit.presentation.navigation.Screen
import com.example.moviedbbit.ui.theme.MovieDBBitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDBBitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieInfoApp()
                }
            }
        }
    }
}

@Composable
fun MovieInfoApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Define which screens should show bottom navigation
    val bottomNavScreens = listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Favorites.route
    )
    
    val showBottomNav = currentDestination?.route in bottomNavScreens

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                NavigationBar {
                    BottomNavItem.items.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = getIconForBottomNavItem(item),
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = currentDestination?.hierarchy?.any { 
                                it.route == item.screen.route 
                            } == true,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        MovieNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun getIconForBottomNavItem(item: BottomNavItem): ImageVector {
    return when (item.icon) {
        "home" -> Icons.Default.Home
        "search" -> Icons.Default.Search
        "favorite" -> Icons.Default.Favorite
        else -> Icons.Default.Home
    }
}