package usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import model.User
import repository.UserRepository
import sun.rmi.server.Dispatcher

class GetUserUseCase(private val userRepository: UserRepository) {

//    operator fun invoke(
//        owner : String,
//        scope : CoroutineScope,
//        onResult : (List<User>) -> Unit = {}
//    ){
//        scope.launch(Dispatchers.Main){
//            val deferred = async(Dispatchers.IO){
//                userRepository.getUserInfo(owner)
//            }
//        }
//    }
    suspend fun getUser(owner:String) : Result<User>{
        return userRepository.getUserInfo(owner)
    }

}