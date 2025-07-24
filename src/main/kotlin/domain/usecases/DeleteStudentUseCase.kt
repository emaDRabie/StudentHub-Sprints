package org.sprints.domain.usecases

import org.sprints.domain.repository.StudentsRepository

class DeleteStudentUseCase(val repository: StudentsRepository) {
    fun deleteById(id: Int?): Boolean {
        return repository.removeStudent(id)
    }

}