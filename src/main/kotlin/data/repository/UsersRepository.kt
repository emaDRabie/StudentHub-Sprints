package org.sprints.data.repository
import org.sprints.domain.models.User
import org.sprints.domain.repository.UsersRepository

class UsersRepository: UsersRepository{
    companion object {
        var users = mutableListOf<User>(
            User("admin", "admin"),
            User("hamdyAdmin", "12345678"),
        )
    }

    override fun login(username: String, password: String): Boolean {
        val admin: User? = users.find { admin -> (admin.password == password && admin.username == username) }
        return admin != null
    }
}