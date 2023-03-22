package smartngo.async.com.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("id")
    val id: Long,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("location")
    val location: String
)
