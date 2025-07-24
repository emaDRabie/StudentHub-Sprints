package org.sprints.data.repository
import org.sprints.data.storage.UserStorage
import org.sprints.domain.models.User
import org.sprints.domain.repository.UsersRepository

class UsersRepository: UsersRepository{
    companion object{
        val users = UserStorage.getUsers()
    }

    override fun login(username: String, password: String): Boolean {
        val admin: User? = users.find { admin -> (admin.password == password && admin.username == username) }
        return admin != null
    }

    override fun signup(username: String, password: String): Boolean {
        val admin: User? = users.find { admin -> (admin.password == password || admin.username == username) }
        if(admin == null){
            users.add(User(username, password))
            UserStorage.saveUsers(users)
            return true
        }else{
            return false
        }
    }

}