package smartngo.async.com.domain.repository

import kotlinx.coroutines.flow.Flow
import smartngo.async.com.core.DataState
import smartngo.async.com.domain.model.User

interface UserRepository {
    fun getAllUsers(): Flow<DataState<List<User>>>
    fun createUser(user: User): Flow<DataState<User>>
    fun asyncLocalToServer(): Flow<DataState<Nothing?>>
}