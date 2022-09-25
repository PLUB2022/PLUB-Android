package repository

import model.User
import java.util.*

interface UserRepository {
    suspend fun getUserInfo(owner : String) : Result<User>
    suspend fun login(loginId : String, loginPassword: String, owner: String) : Boolean
}