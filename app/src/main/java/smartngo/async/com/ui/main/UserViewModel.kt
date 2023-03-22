package smartngo.async.com.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import smartngo.async.com.core.DataState
import smartngo.async.com.domain.model.User
import smartngo.async.com.domain.repository.UserRepository
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _users = MutableLiveData<DataState<List<User>>>(DataState.Empty)
    val users: LiveData<DataState<List<User>>> = _users

    private val _addUser = MutableLiveData<DataState<User>>(DataState.Empty)
    val addUser: LiveData<DataState<User>> = _addUser

    private val _asyncState = MutableLiveData<DataState<Nothing?>>(DataState.Empty)
    val asyncState: LiveData<DataState<Nothing?>> = _asyncState

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch {
            userRepository.getAllUsers().collect{response ->
                _users.value = response
            }
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            userRepository.createUser(user).collect{ response ->
                _addUser.value = response
            }
        }
    }

    fun asyncUserData() {
        viewModelScope.launch {
            userRepository.asyncLocalToServer().collect{
                _asyncState.value = it
            }
        }
    }
}