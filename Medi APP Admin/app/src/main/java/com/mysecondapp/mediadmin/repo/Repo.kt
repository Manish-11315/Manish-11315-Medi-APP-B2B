package com.mysecondapp.mediadmin.repo

import com.mysecondapp.mediadmin.api.ApiBuilder
import com.mysecondapp.mediadmin.common.Results
import com.mysecondapp.mediadmin.model.AddProductDataModel
import com.mysecondapp.mediadmin.model.UserDataModel
import com.mysecondapp.mediadmin.model.UserDataModelItem
import com.mysecondapp.mediadmin.model.UserOperationModel
import com.mysecondapp.mediadmin.viewmodel.apistate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class Repo @Inject constructor(private val ApiInstance: ApiBuilder) {

    private val _userState = MutableStateFlow(apistate())
    val UserAllDataState : Flow<apistate> = _userState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            ListAlluser()
        }
    }
    suspend fun ListAlluser(){
        _userState.emit(apistate(loading = true))
        try {

            val userdata = ApiInstance.Api.GetAllUsersData()
            if (userdata.isSuccessful){
                _userState.emit(apistate(data = userdata.body(), loading = false))
            }else{
                _userState.emit(apistate(error = "An Error ${userdata.message()}"))
            }

        }catch (e : Exception){
            _userState.emit(apistate(error = e.message))
        }
    }

    /*suspend fun ListAllUsersData(): Flow<Results<Response<UserDataModel>>> = flow {
        emit(Results.Loading)
        try {
            val data: Response<UserDataModel> = ApiInstance.Api.GetAllUsersData()
            emit(Results.Success(data))

        } catch (e: Exception) {
            emit(Results.Error(Errormsg = e.message.toString()))
        }
    }*/

    suspend fun ListSpecificUser(uid : String) : Flow<Results<Response<UserDataModelItem>>> = flow {
        emit(Results.Loading)
        try {
            val data = ApiInstance.Api.GetSpecificUser(uid)
            if (data.isSuccessful) {
                emit(Results.Success(data))
            }else{
                emit(Results.Error(Errormsg = "Api Error : ${data.message()}"))
            }
        }catch (e : Exception){
            emit(Results.Error(e.message.toString()))
        }
    }

    suspend fun DeleteSpecificUser(Uid : String) : Flow<Results<Response<UserDataModel>>> = flow {
        emit(Results.Loading)

        val currentlist = _userState.value.data?.toMutableList() ?: mutableListOf()
        val userToremove = currentlist.find { it.user_id == Uid }

        if (userToremove != null){
            currentlist.remove(userToremove)
            val newdatamode = UserDataModel()
            newdatamode.addAll(currentlist)
            _userState.emit(apistate(data = newdatamode))
        }

        try {
            val data = ApiInstance.Api.deleteSpecificUser(Uid)
            if (data.isSuccessful){
                ListAlluser()
                emit(Results.Success(data))
            }else{
                emit(Results.Error("Api Error : ${data.message()}"))
            }
        }catch (e : Exception){
            emit(Results.Error(e.message.toString()))
        }
    }



    suspend fun approveuser(Uid: String, ApproveStatus : String) : Flow<Results<Response<UserOperationModel>>> = flow {
        emit(Results.Loading)
        try {
            val UserApproval=ApiInstance.Api.ApproveUser(UserId = Uid, ApproveStatus =  ApproveStatus)
            if (UserApproval.isSuccessful){
                ListAlluser()
                emit(Results.Success(data = UserApproval))
            }
        }catch (e : Exception){
            emit(Results.Error(e.message.toString()))
        }
    }

    suspend fun Manageuser(Uid: String?, Block_Status : String? ) : Flow<Results<Response<UserOperationModel>>> = flow {
        emit(Results.Loading)
        try {
            val updatestatus = ApiInstance.Api.updateUser(UserId = Uid, BlockStatus = Block_Status)
            if (updatestatus.isSuccessful){
                ListAlluser()
                emit(Results.Success(data = updatestatus))
            }
        }catch (e : Exception){
            emit(Results.Error(Errormsg = e.message.toString()))
        }
    }

    suspend fun addProduct(
        name : String,
        price : Float,
        category : String,
        stock : Int
    ) : Flow<Results<Response<AddProductDataModel>>> = flow {
        emit(Results.Loading)
        try {
            val response = ApiInstance.Api.AddProduct(Name = name, Price = price, Category = category, Stock = stock)
            emit(Results.Success(data = response))
        }catch (e : Error){
            emit(Results.Error(e.message.toString()))
        }
    }
}
