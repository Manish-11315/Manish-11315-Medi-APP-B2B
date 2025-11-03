package com.mysecondapp.mediadmin.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mysecondapp.mediadmin.R
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.mysecondapp.mediadmin.ui.theme.poppins
import com.mysecondapp.mediadmin.viewmodel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAllProducts(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {

    val state = viewModel.GetProductStateHolder.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Column {
                    Text("All Products Screen", fontSize = 20.sp, fontFamily = poppins, fontWeight = FontWeight.Bold)
                    Text(text = "Total Products Count : ${state.value.data?.count()}")
                    } 
                },
                colors = TopAppBarColors(
                    containerColor = colorResource(id = R.color.purple_200),
                    scrolledContainerColor = TODO(),
                    navigationIconContentColor = TODO(),
                    titleContentColor = TODO(),
                    actionIconContentColor = TODO(),
                    subtitleContentColor = TODO()
                )
            )
        }
    ) {innerpadding->
        Box(
            modifier = Modifier.padding(innerpadding)
        ){
            
        }
    }
}