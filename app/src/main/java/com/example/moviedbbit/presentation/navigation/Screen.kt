package com.example.moviedbbit.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Details : Screen("details/{movieId}") {
        fun createRoute(movieId: Int) = "details/$movieId"
    }
    object Favorites : Screen("favorites")
}

sealed class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: String // We'll use string names for Material Icons
) {
    object Home : BottomNavItem(
        screen = Screen.Home,
        title = "Home",
        icon = "home"
    )
    
    object Search : BottomNavItem(
        screen = Screen.Search,
        title = "Search",
        icon = "search"
    )
    
    object Favorites : BottomNavItem(
        screen = Screen.Favorites,
        title = "Favorites",
        icon = "favorite"
    )
    
    companion object {
        val items = listOf(Home, Search, Favorites)
    }
}