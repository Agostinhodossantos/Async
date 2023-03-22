package music.rizzystana.myasync.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import smartngo.async.com.core.USERS_TABLE
import smartngo.async.com.data.database.UsersDb


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideQuestionDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        UsersDb::class.java,
        USERS_TABLE
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()


    @Provides
    fun provideUsersDao(
        usersDb: UsersDb
    ) = usersDb.usersDao()

}