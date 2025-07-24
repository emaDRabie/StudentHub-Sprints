package org.sprints.domain.usecases

import org.sprints.domain.models.Student
import org.sprints.domain.repository.StudentsRepository

class FilterStudentsUseCase(val repository: StudentsRepository) {
    fun filterByName(name: String?): List<Student> {
        return repository.filterStudent(name = name, status = null, grade = null)
    }

    fun filterByStatus(status: String?): List<Student> {
        return repository.filterStudent(name = null, status = status, grade = null)
    }

    fun filterByGrade(grade: String?): List<Student> {
        return repository.filterStudent(name = null, status = null, grade = grade)
    }

    fun filterByGPA(minGPA: Double, maxGPA: Double): List<Student> {
        return repository.filterStudentsByGPA(minGPA, maxGPA)
    }
}