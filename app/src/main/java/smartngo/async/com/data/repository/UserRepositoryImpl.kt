package smartngo.async.com.data.repository

import kotlinx.coroutines.flow.flow
import smartngo.async.com.core.DataState
import smartngo.async.com.data.database.CacheMapper
import smartngo.async.com.data.database.UsersDao
import smartngo.async.com.data.network.UserApi
import smartngo.async.com.domain.model.User
import smartngo.async.com.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val usersDao: UsersDao
): UserRepository {
    override fun getAllUsers() = flow {
        emit(DataState.Loading)

        // get data from api and insert into local database
        try {
            var networkUsers = api.getUsers()
            networkUsers.forEach {  currentUser ->
                usersDao.setUser(CacheMapper().mapToEntity(currentUser))
            }
        } catch (e: Exception) { }

        try {
           val localUsers = CacheMapper().mapToList(usersDao.getAllUsers())
           emit(DataState.Success(localUsers))
       }catch (e: Exception) {
           emit(DataState.Error(e.message ?: e.toString()))
       }
    }

    override fun createUser(user: User) = flow {

        try {
            var response = api.setUser(user)
        } catch (e: Exception) {}

        try {
            emit(DataState.Loading)
            usersDao.setUser(CacheMapper().mapToEntity(user))
            emit(DataState.Success(user))
        }catch (e: Exception) {
            emit(DataState.Error(e.message ?: e.toString()))
        }
    }

    override fun asyncLocalToServer() = flow {
        emit(DataState.Loading)

        try {
            val localUsers = CacheMapper().mapToList(usersDao.getAllUsers())

            localUsers.forEach {user ->
                var response = api.setUser(user)
                Timber.e(response.toString())
            }
            emit(DataState.Success(null))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: e.toString()))
        }

    }

}