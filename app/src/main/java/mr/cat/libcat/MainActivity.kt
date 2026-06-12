package mr.cat.libcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mr.cat.libcat.screen.*
import mr.cat.libcat.ui.theme.LibCatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibCatTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainScreen(
                onNavigateToDetail = { navController.navigate("detail") },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToShowcase = { navController.navigate("showcase") },
                onNavigateToWebView = { url -> navController.navigate("webview?url=$url") }
            )
        }
        composable("detail") {
            ArticleDetailScreen(onBack = { navController.popBackStack() })
        }
        composable("profile") {
            ProfileScreen(onBack = { navController.popBackStack() })
        }
        composable("showcase") {
            SettingsShowcaseScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = "webview?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: "https://www.google.com"
            WebViewScreen(url = url, onBack = { navController.popBackStack() })
        }
    }
}
