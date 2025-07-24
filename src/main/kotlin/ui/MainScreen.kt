package org.sprints.ui

import org.sprints.data.repository.StudentsRepository
import org.sprints.domain.models.Student
import org.sprints.domain.usecases.GetAllStudentsUseCase
import org.sprints.domain.usecases.AddNewStudentUseCase
import org.sprints.domain.usecases.DeleteStudentUseCase


class MainScreen {
    private val getAllStudentsUseCase = GetAllStudentsUseCase(StudentsRepository())
    private val addNewStudentUseCase = AddNewStudentUseCase(StudentsRepository())
    private val deletestudentusecase = DeleteStudentUseCase(StudentsRepository())


    fun home() {
        println("Welcome to Students Management System")
        if (!login()) return
        println(
            """
            please select what are you want to do : 
            0 - Add New Student
            1 - Remove New Student
            2 - Search Students
            3 - Update Student Info
            4 - Show all Students
            5 - Exit
        """.trimIndent()
        )
        val input = readlnOrNull()?.toIntOrNull()
        val option = input?.let { Options.entries.getOrNull(it) }

        when (option) {
            Options.ADD_STUDENT -> addNewStudent()
            Options.UPDATE_STUDENT -> updateStudents()
            Options.REMOVE_STUDENT -> removeStudents()
           // Options.FILTER_STUDENT -> filterStudents()
            Options.GET_STUDENTS -> getStudents()
            Options.EXIT -> {
                println("Exiting...")
                return
            }

            else -> println("Invalid option. Try again.")

        }


    }

    private fun login(): Boolean {
        println("please enter your username")
        val username = readlnOrNull()
        println("Enter your password")
        val password = readlnOrNull()
        if (username == null || password == null) return false

        println("Logged in as $username")
        return true
    }

    private fun addNewStudent(): Boolean {
        println("Enter student Name ")
        val name = readlnOrNull()
        if (name.isNullOrBlank()) {
            println("Name is required.")
            return false
        }
        println("Enter student grade")
        val grade = readlnOrNull()
        if (grade.isNullOrBlank()) {
            println("Grade is required.")
            return false
        }
        println("Enter student GPA ")
        val gpaInput = readlnOrNull()
        val gpa = gpaInput?.toDoubleOrNull()
        if (gpaInput.isNullOrBlank()) {
            println("GPA is required.")
            return false
        }
        if (gpa == null) {
            println("Invalid GPA. Please enter a valid number.")
            return false
        }
        println("Enter student Note")
        val note = readlnOrNull()
        println("Enter student status ")
        val status = readlnOrNull()
        if (status.isNullOrBlank()) {
            println("Status is required.")
            return false
        }
        val students = getAllStudentsUseCase.getAllStudents()
        val newId = (students.maxOfOrNull { it.id } ?: 0) + 1
        val student = Student(
            id = newId,
            name = name,
            grade = grade,
            status = status,
            gpa = gpa,
            notes = note
        )
        val result = addNewStudentUseCase.isStudentAdded(student)
        if (result) {
            println("Student added successfully!")
            getStudents()
        } else {
            println("Failed to add student.")
        }
        return result
    }

    private fun removeStudents(): Boolean {
        // show all students
        print("Enter student ID ▶ ")
        val id = readlnOrNull()?.toIntOrNull()
        val deleteid = deletestudentusecase.deleteById(id)
        if (deleteid) {
            println("Student deleted successfully!")
        } else {
            println("student not found")
        }

        return false
    }

    private fun filterStudents(): List<Student> {
        println(
            """
            please select what are you want to do : 
            0 - filter by name
            1 - filter by grade
            2 - filter by status            
            3 - Back
        """.trimIndent()
        )
        val filterOp: Int = readlnOrNull()!!.toInt()

        when (filterOp) {
            0 -> {
                println("Enter student's name: ")
                val name = readlnOrNull()
                // handle filter use case here
            }

            1 -> {
                println("Enter student's grade: ")
                val grade = readlnOrNull()
                // handle filter use case here
            }

            2 -> {
                println("Enter student's status: ")
                val status = readlnOrNull()
                // handle filter use case here
            }
        }

        return listOf()
    }

    private fun getStudents() {
        val idWidth = 5
        val nameWidth = 20
        val gradeWidth = 16
        val gpaWidth = 12
        val statusWidth = 14
        val notesWidth = 30
        println(
            String.format(
                "%-${idWidth}s %-${nameWidth}s %-${gradeWidth}s %-${gpaWidth}s %-${statusWidth}s %-${notesWidth}s",
                "ID", "Name", "Grade", "GPA", "Status", "Notes"
            )
        )
        println("-".repeat(idWidth + nameWidth + gradeWidth + gpaWidth + statusWidth + notesWidth + 5))

        getAllStudentsUseCase.getAllStudents().forEach {
            println(
                String.format(
                    "%-${idWidth}d %-${nameWidth}s %-${gradeWidth}s %-${gpaWidth}.2f %-${statusWidth}s %-${notesWidth}s",
                    it.id, it.name, it.grade, it.gpa, it.status, it.notes ?: ""
                )
            )
        }
    }

    private fun updateStudents() {

    }

}