package org.sprints.data.repository
import org.sprints.domain.models.User
import org.sprints.domain.repository.UsersRepository

class UsersRepository: UsersRepository{
    companion object {
        var user = User("admin", "admin")
    }
}