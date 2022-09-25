package repository

import model.User
import model.UserRepos
import source.UserRemoteSource
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserRemoteSource
) : UserRepository{
    override suspend fun getUserInfo(owner : String): Result<UserRepos> {
        return userRemoteSource.getUser(owner)
    }

    override suspend fun login(loginId: String, loginPassword: String, owner: String): Boolean {
        //TODO("Not yet implemented")
        return userRemoteSource.login(loginId, loginPassword)
        //????? 이게 무슨 근본없는 코드지
    }
}