package smartngo.async.com.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import smartngo.async.com.data.database.UserCache
import smartngo.async.com.data.database.UsersDao

@Database(entities = [UserCache::class], version = 1, exportSchema = false)
abstract class UsersDb: RoomDatabase(){
    abstract fun usersDao(): UsersDao
}