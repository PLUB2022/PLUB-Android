package usecase

import model.User
import repository.UserRepository
import java.util.*

class LoginUseCase (private val userRepository: UserRepository){

    suspend fun login(loginId: String, loginPassword: String, owner:String) : Result<User>{
        return userRepository.login(loginId, loginPassword, owner)
    }
}