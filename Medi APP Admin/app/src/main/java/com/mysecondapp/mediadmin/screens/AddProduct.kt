package com.mysecondapp.mediadmin.screens

import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.mysecondapp.mediadmin.R
import com.mysecondapp.mediadmin.ui.theme.poppins
import com.mysecondapp.mediadmin.viewmodel.MyViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalLayoutApi::class)
@Composable
fun Addproduct(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {
    val addproductstate = viewModel.ProductStateHolder.collectAsState()

    val name = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val stock = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val toastContext = LocalContext.current

    Scaffold {innerpadding->
        Box(
            modifier = Modifier.padding(innerpadding)
        ){
            if (addproductstate.value.loading == true){
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                    CircularWavyProgressIndicator(color = colorResource(id = R.color.purple_200))
                }
            }else if (addproductstate.value.error != null){
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                    Text(text = "An Error Occurred : ${addproductstate.value.error.toString()}")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .imeNestedScroll()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "ADD Product ",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                    },
                    label = {
                        Text(text = "Enter the Medicine Name", fontFamily = poppins)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = category.value,
                    onValueChange = {
                        category.value = it
                    },
                    label = {
                        Text(text = "Enter the Medicine category")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = price.value,
                    onValueChange = {
                        if (it.length <= 6){
                            price.value = it
                        }
                    },
                    label = {
                        Text(text = "Enter the Price")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = stock.value,
                    onValueChange = {
                        if (it.length <= 5){
                            stock.value = it
                        }
                    },
                    label = {
                        Text(text = "Enter the Stock")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        if (!name.value.isNullOrBlank() &&
                            !category.value.isNullOrBlank()&&
                            !price.value.isNullOrBlank()&&
                            !stock.value.isNullOrBlank()){

                            val stockint : Int = stock.value.toInt()
                            val pricefloat : Float = price.value.toFloat()
                            viewModel.addProduct(name = name.value, category = category.value, price = pricefloat, stock = stockint)
                            Toast.makeText(toastContext, "Successfully Added Product stock ",Toast.LENGTH_SHORT).show()

                        }else{
                            Toast.makeText(toastContext, "Please Fill all the details", Toast.LENGTH_SHORT).show()

                        }
                    }
                ) {
                    Text("Add Product")

                }

            }
        }
    }

}