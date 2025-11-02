package com.codergalib2005.composereorderable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codergalib2005.composereorderable.reorderlist.ReorderGrid
import com.codergalib2005.composereorderable.reorderlist.ReorderImageList
import com.codergalib2005.composereorderable.reorderlist.ReorderList
import com.codergalib2005.composereorderable.ui.theme.ComposeReOrderableTheme
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Root() {
    ComposeReOrderableTheme {
        val navController = rememberNavController()
        val navigationItems = listOf(
            NavigationItem.Lists,
            NavigationItem.Grids,
            NavigationItem.Fixed,
        )
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Compose Reorderable") })
            },
            bottomBar = {
                BottomNavigationBar(navigationItems, navController)
            }
        ) {
            Navigation(
                navController,
                Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            )
        }
    }
}

@Composable
private fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = NavigationItem.Lists.route, modifier = modifier) {
        composable(NavigationItem.Lists.route) { ReorderList() }
        composable(NavigationItem.Grids.route) { ReorderGrid() }
        composable(NavigationItem.Fixed.route) { ReorderImageList() }
    }
}

@Composable
private fun BottomNavigationBar(items: List<NavigationItem>, navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}


// Serializable route definitions (no icons here)
@Serializable
private sealed class NavigationItem(val route: String, val title: String) {
    @Serializable
    object Lists : NavigationItem("lists", "Lists")

    @Serializable
    object Grids : NavigationItem("grids", "Grids")

    @Serializable
    object Fixed : NavigationItem("fixed", "Fixed")
}

// Non-serializable icon mapping
private val NavigationItem.icon: ImageVector
    get() = when (this) {
        NavigationItem.Lists -> Icons.AutoMirrored.Filled.List
        NavigationItem.Grids -> Icons.Default.Settings
        NavigationItem.Fixed -> Icons.Default.Star
    }
