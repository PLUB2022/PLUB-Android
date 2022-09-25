package repository

import model.User
import java.util.*

interface UserRepository {
    suspend fun getUserInfo(owner : String) : List<User>
    suspend fun login(loginId : String, loginPassword: String, owner: String) : Result<User>
}