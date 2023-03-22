package smartngo.async.com.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import smartngo.async.com.core.USERS_TABLE


@Entity(tableName = USERS_TABLE)
data class UserCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val location: String
)
