package smartngo.async.com.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import smartngo.async.com.data.database.UsersDao
import smartngo.async.com.data.network.UserApi
import smartngo.async.com.data.repository.UserRepositoryImpl
import smartngo.async.com.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userApi: UserApi,
        usersDao: UsersDao
    ): UserRepository {
        return UserRepositoryImpl(userApi, usersDao)
    }
}