package com.mysecondapp.mediadmin.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mysecondapp.mediadmin.screens.Addproduct
import com.mysecondapp.mediadmin.screens.ShowAllProducts
import com.mysecondapp.mediadmin.screens.ShowAllUserScreen
import com.mysecondapp.mediadmin.screens.ShowSingleUserScreen

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.AddproductRoute,
    ) {
        composable<Routes.AllUserScreenRoute> { it ->
            ShowAllUserScreen(navController = navController)
        }
        composable<Routes.UserDataScreenRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<Routes.UserDataScreenRoute>()
            ShowSingleUserScreen(navController = navController, uId = args.userId)
        }
        composable<Routes.AddproductRoute>{
            Addproduct(navController = navController)
        }
        composable<Routes.GetProductsRoute> {
            ShowAllProducts(navController = navController)
        }

    }
}