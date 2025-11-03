package com.mysecondapp.mediadmin.screens.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    object AllUserScreenRoute

    @Serializable
    data class UserDataScreenRoute(
        val userId : String?
    )

    @Serializable
    object AddproductRoute

}