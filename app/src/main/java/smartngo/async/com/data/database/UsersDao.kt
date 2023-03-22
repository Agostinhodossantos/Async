package smartngo.async.com.data.database

import androidx.room.*
import smartngo.async.com.core.USERS_TABLE
import smartngo.async.com.data.database.UserCache

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUser(userCache: UserCache)

    @Query("SELECT * FROM $USERS_TABLE ORDER BY id ASC")
    fun getAllUsers(): List<UserCache>
}