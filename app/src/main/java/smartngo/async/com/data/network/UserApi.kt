package smartngo.async.com.data.network

import smartngo.async.com.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    @Headers("Content-Type: application/json")
    @POST("users")
    suspend fun setUser(@Body user: User) : User

}