package com.mysecondapp.mediadmin.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mysecondapp.mediadmin.R
import com.mysecondapp.mediadmin.viewmodel.MyViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowSingleUserScreen(
    navController: NavController,
    uId: String?,
    viewModel: MyViewModel = hiltViewModel(),
) {

    val toastcontext = LocalContext.current

    val systemUiController = rememberSystemUiController()
    val darktheme = isSystemInDarkTheme()

    LaunchedEffect(key1 = uId) {
        viewModel.showUserDetails(uId)
    }

    val deleteState = viewModel.DeleteUserState.collectAsState()

    LaunchedEffect(deleteState.value) {
        val state = deleteState.value
        if (state.data != null && state.loading == false){
            Toast.makeText(toastcontext, "User Deleted", Toast.LENGTH_SHORT).show()
            navController.popBackStack()


        }else if (state.error != null){
            Log.d("Local Tag", state.error)
            Toast.makeText(toastcontext, "Error Arises : ${state.error}", Toast.LENGTH_SHORT)
                .show()
        }
    }


    val userState = viewModel.SingleUserState.collectAsState()

    val approveState = viewModel.ApproveUserStateHolder.collectAsState()

    val manageUserstatevalue = viewModel.ManageUserStateHolder.collectAsState()

    val isBlocked = remember { mutableStateOf(false) }
    if (userState.value.data?.block_status == 1){
        isBlocked.value = true
    }

    val isApproved = remember { mutableStateOf(false) }
    if (userState.value.data?.is_approved == 1){
        isApproved.value = true
    }


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
        topBar = {
            Column {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.purple_200),
                        navigationIconContentColor = if (darktheme) {
                            colorResource(id = R.color.black)
                        } else {
                            colorResource(id = R.color.white)

                        },
                        titleContentColor = if (darktheme) {
                            colorResource(id = R.color.black)
                        } else {
                            colorResource(id = R.color.white)

                        },
                    ),
                    title = { Text("User Details") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    ) { innerpadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding),

            ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                if (userState.value.loading == true) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularWavyProgressIndicator(color = colorResource(R.color.purple_200))
                    }
                } else if (userState.value.error != null) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Text(text = userState.value.error.toString())
                    }
                } else if (userState.value.data != null) {
                    val data = userState.value.data

                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp)
                    ) {
                        Text(text = "Name : ${data!!.name}")
                        Text(text = "Password : ${data!!.password}")
                        Text(text = "User ID : ${data!!.user_id}", overflow = TextOverflow.Visible)
                        Text(text = "Email : ${data!!.email}")
                        Text(text = "Date of Account Creation : ${data!!.date_of_creation}")
                        Text(text = "Phone Number : ${data!!.phone_number}")
                        Text(text = "Address : ${data!!.address}")
                        Text(text = "PIN Code : ${data!!.pincode}")
                        if (isBlocked.value) {
                            Text(text = "Block Status : Blocked")
                        } else {
                            Text(text = "Block Status : Not Blocked")
                        }
                        if (isApproved.value) {
                            Text(text = "Approved Status : Approved")
                        } else {
                            Text(text = "Approved Status : Not Approved")
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if (isBlocked.value){

                                    viewModel.ManageUser(UserId = uId, BlockStatus = "0")
                                    isBlocked.value = false
                                }else{
                                    viewModel.ManageUser(UserId = uId, BlockStatus = "1")
                                    isBlocked.value = true
                                }
                                navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)

                            },
                            colors = if (isBlocked.value) {ButtonDefaults.buttonColors(containerColor = Color.Green)}else{ButtonDefaults.buttonColors(containerColor = Color.Red)},
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                        ){
                            Icon(
                                imageVector = if (isBlocked.value) {Icons.Filled.Approval}else{Icons.Filled.RemoveCircle},
                                contentDescription = "Unblock User",
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                            Text(if (isBlocked.value) { "Unblock"}else{"Block"})
                        }

//                        Spacer(modifier = Modifier.padding(2.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                if(isApproved.value){
                                    viewModel.deleteUser(UID = uId)

                                }else{
                                    viewModel.ApproveUser(UserId = uId!!, ApproveStatus = "1")
                                    isApproved.value = true
                                    navController.previousBackStackEntry?.savedStateHandle?.set("refresh", true)
                                }


                            },
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            ),
                            colors = if (isApproved.value){ButtonDefaults.buttonColors(containerColor = Color.Red)}else{ButtonDefaults.buttonColors(containerColor = Color.Green)}

                        ) {
                            Icon(
                                contentDescription = null,
                                imageVector = if (isApproved.value){Icons.Default.Delete}else{Icons.Default.Check},
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
                            Text(text = if (isApproved.value){"Delete"}else{"Approve"})
                        }

                    }
                }
            }
        }
    }
}