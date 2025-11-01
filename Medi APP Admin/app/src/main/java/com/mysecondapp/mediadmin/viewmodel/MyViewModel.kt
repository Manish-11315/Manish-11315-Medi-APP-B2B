package com.mysecondapp.mediadmin.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysecondapp.mediadmin.common.Results
import com.mysecondapp.mediadmin.model.UserDataModel
import com.mysecondapp.mediadmin.model.UserDataModelItem
import com.mysecondapp.mediadmin.model.UserOperationModel
import com.mysecondapp.mediadmin.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.http.Field
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val RepoObj : Repo) : ViewModel () {


    val ListUsers = RepoObj.UserAllDataState

    private val _SingleUserState = MutableStateFlow(apistate_SingleUserData())
    val SingleUserState = _SingleUserState.asStateFlow()

    private val _deleteUserState = MutableStateFlow(deleteUserState())
    val DeleteUserState = _deleteUserState.asStateFlow()

    private val _ApproveUserStateHolder = MutableStateFlow(ApproveUserState())
    val ApproveUserStateHolder = _ApproveUserStateHolder.asStateFlow()

    private val _ManagestatestateHolder = MutableStateFlow(ManageUserState())
    val ManageUserStateHolder = _ManagestatestateHolder.asStateFlow()

    val _listUsers = MutableStateFlow(apistate())
    val listUsers = _listUsers.asStateFlow()


    fun showUserDetails(UID : String?){
        if (UID == null) return
        viewModelScope.launch(Dispatchers.IO) {
            RepoObj.ListSpecificUser(uid = UID!!).onStart{
                _SingleUserState.value = apistate_SingleUserData(loading = true)
            }.catch { e->
                _SingleUserState.value = apistate_SingleUserData(loading = false, error = "Failed to Load the Data : ${ e.message.toString() }")
            }.collect { result ->
                when(result){
                    is Results.Error -> {
                        _SingleUserState.value = apistate_SingleUserData(loading = false, error = result.Errormsg)
                    }
                    Results.Loading -> {
                        _SingleUserState.value = apistate_SingleUserData(loading = true)
                    }
                    is Results.Success -> {
                        _SingleUserState.value = apistate_SingleUserData(loading = false, data = result.data.body())
                    }
                }
            }
        }
    }

    fun deleteUser(UID: String?){
        if (UID == null) return
        viewModelScope.launch(Dispatchers.IO) {
            RepoObj.DeleteSpecificUser(UID!!).collect { it->
                when(it){
                    is Results.Error -> {
                        _deleteUserState.value = deleteUserState(loading = false, error = it.Errormsg)
                    }
                    is Results.Loading -> {
                        _deleteUserState.value = deleteUserState(loading = true)
                    }
                    is Results.Success -> {
                        _deleteUserState.value = deleteUserState(loading = false, data = it.data.body())
                    }

                }
            }

        }
    }


    fun ApproveUser(UserId: String,ApproveStatus : String){
        viewModelScope.launch (Dispatchers.IO){
            RepoObj.approveuser(Uid = UserId, ApproveStatus = ApproveStatus).collect { it->
                when(it){
                    is Results.Loading -> {
                        _ApproveUserStateHolder.value = ApproveUserState(loading = true)
                    }
                    is Results.Error -> {
                        _ApproveUserStateHolder.value = ApproveUserState(loading = false, error = it.Errormsg)
                    }
                    is Results.Success -> {
                        _ApproveUserStateHolder.value = ApproveUserState(loading = false, data = it.data.body())
                    }
                }
            }
        }
    }

    fun ManageUser(UserId: String?, BlockStatus : String?){
        viewModelScope.launch(Dispatchers.IO) {
            RepoObj.Manageuser(Uid = UserId, Block_Status = BlockStatus).collect { it->
                when(it){
                    is Results.Loading -> {
                        _ManagestatestateHolder.value = ManageUserState(loading = true)
                    }
                    is Results.Error -> {
                        _ManagestatestateHolder.value = ManageUserState(loading = false, error = it.Errormsg)

                    }
                    is Results.Success -> {
                        _ManagestatestateHolder.value = ManageUserState(loading = false , data =  it.data.body())
                    }
                }
            }
        }
    }

}
data class apistate(
    val loading : Boolean? = false,
    val error: String? = null,
    val data : UserDataModel? = null
)
data class apistate_SingleUserData(
    val loading : Boolean? = false,
    val error: String? = null,
    val data : UserDataModelItem? = null
)
data class deleteUserState(
    val loading: Boolean? = false,
    val error: String? = null,
    val data: UserDataModel? = null
)
data class ApproveUserState(
    val loading: Boolean? = false,
    val error: String? = null,
    val data: UserOperationModel? = null
)
data class ManageUserState(
    val loading : Boolean? = false,
    val error: String? = null,
    val data : UserOperationModel? = null
)