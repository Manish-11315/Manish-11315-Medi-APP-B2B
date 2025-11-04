package com.mysecondapp.mediadmin.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mysecondapp.mediadmin.R
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.mysecondapp.mediadmin.ui.theme.poppins
import com.mysecondapp.mediadmin.viewmodel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowAllProducts(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {

    val state = viewModel.GetProductStateHolder.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Column {
                    Text("All Products Screen", fontSize = 20.sp, fontFamily = poppins, fontWeight = FontWeight.Bold)
                    } 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.purple_200),

                )
            )
        }
    ) {innerpadding->
        Box(
            modifier = Modifier.padding(innerpadding)
        ){
            if (state.value.loading == true){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularWavyProgressIndicator()
                }
            }else if (state.value.error != null){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), contentAlignment = Alignment.Center){
                    Text(
                        text = state.value.error.toString()
                    )
                }
            }else{
                val data = state.value.data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ){
                    Text("Total Products Count : ${data?.count()}")
                    LazyColumn  {
                        items(data  ?: emptyList()) {item->
                            Card(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                                shape = RoundedCornerShape(corner = CornerSize(12.dp))
                            ) {
                                Column(
                                    modifier = Modifier.wrapContentSize().padding(12.dp)
                                ){
                                    Text(text = "Name : ${item.name}")
                                    Text(text = "Category : ${item.category}")
                                    Text(text = "Price : ${item.price}")
                                    Text(text = "Stock : ${item.stock}")
                                }
                            }
                        }
                    }
                }
            }
            
        }
    }
}