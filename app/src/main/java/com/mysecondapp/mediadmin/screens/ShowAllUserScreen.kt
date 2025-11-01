package com.mysecondapp.mediadmin.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mysecondapp.mediadmin.R
import com.mysecondapp.mediadmin.common.Results
import com.mysecondapp.mediadmin.model.UserDataModel
import com.mysecondapp.mediadmin.screens.navigation.Routes
import com.mysecondapp.mediadmin.viewmodel.MyViewModel
import com.mysecondapp.mediadmin.viewmodel.apistate
import kotlinx.serialization.descriptors.StructureKind

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowAllUserScreen(viewModel: MyViewModel = hiltViewModel(),navController: NavController) {
    val data = viewModel.ListUsers.collectAsState(initial = apistate(loading = true))

    val systemUiController = rememberSystemUiController()
    val darktheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            darkIcons = if (darktheme) {
                true
            } else {
                false

            },
            color = if (darktheme) {
                Color.Transparent
            } else {
                Color.Transparent
            },
        )
    }



    Scaffold(
        modifier = Modifier.fillMaxSize() ,
        topBar = {
            Column() {
                TopAppBar(
                    title = { Text("All Users Screen") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.purple_200),
                        navigationIconContentColor = colorResource(id = R.color.black),
                        titleContentColor = if (darktheme) {
                            colorResource(id = R.color.black)
                        } else {
                            colorResource(id = R.color.white)

                        },
                    )

                )
            }
        }

    ) {innerpadding->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
        ) {

            if (data.value.loading == true){
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                    CircularWavyProgressIndicator(color = colorResource(id = R.color.purple_200))
                }
            }else if(data.value.error != null){
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(10.dp)){
                    Text(text = data.value.error.toString())
                }
            }else if(data.value.data != null){
                val users: UserDataModel? = data.value.data
                Column(modifier = Modifier.fillMaxSize().padding(5.dp)){
                    Text("Total User Count : ${users!!.count()} ", fontWeight = FontWeight.ExtraBold)
                    LazyColumn (
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(users!!){user->
                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .offset(y = 2.dp, x = 0.dp)
                                    .background(color = colorResource(id = R.color.purple_200).copy(alpha = 0.2f))
                            ) {
                                OutlinedCard(
                                    modifier = Modifier.fillMaxWidth().padding(12.dp)
                                        .wrapContentHeight(),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                                    shape = RoundedCornerShape(corner = CornerSize(25.dp)),
                                    border = BorderStroke(
                                        1.dp,
                                        color = colorResource(id = R.color.purple_200)
                                    ),
                                    onClick = {
                                        navController.navigate(Routes.UserDataScreenRoute(userId = user.user_id))
                                    }
                                    ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(15.dp)
                                    ) {
                                        Text(text = "User ID : ${user.user_id}")
                                        Text(text = "User Name : ${user.name}")
                                        Text(text = "Password : ${user.password}")
                                        Text(text = "Email : ${user.email}")
                                        Text(text = "Address : ${user.address}")
                                        Text(text = "Pin-code : ${user.pincode}")
                                        Text(text = "Phone Number : ${user.phone_number}")
                                        Text(text = "User Approval : ${user.is_approved}")
                                        Text(text = "Block Status : ${user.block_status}")
                                        Text(text = "Account Creation Date : ${user.date_of_creation}")
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
    }

}