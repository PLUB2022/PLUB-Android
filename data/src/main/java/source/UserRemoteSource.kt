package source

import model.User
import model.UserRepos
import service.UserService
import javax.inject.Inject

interface UserRemoteSource {
    suspend fun getUser(owner : String) : List<UserRepos>
    fun login(loginId : String, loginPassword : String) : Result<UserRepos>
}

class UserRemoteSourceImpl @Inject constructor(
    private val userService: UserService
) : UserRemoteSource{

    override suspend fun getUser(owner: String): List<UserRepos> {
        return userService.getUser(owner)
    }

    override fun login(loginId: String, loginPassword: String) : Result<UserRepos>{

        //TODO 서버에서 로그인 데이터 일치한지 확인
        //return userService.isLogin(loginId, loginPassword)
    }

}