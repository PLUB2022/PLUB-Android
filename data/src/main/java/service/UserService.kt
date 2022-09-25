package service

import model.UserRepos
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{owner}")
    suspend fun getUser(@Path("owner") owner : String) : List<UserRepos>

}