package com.mysecondapp.mediadmin.common

sealed class Results<out T> {
    object Loading : Results<Nothing>()
    data class Success<out T>(val data : T) : Results<T>()
    data class Error (val Errormsg : String) : Results<Nothing>()
}