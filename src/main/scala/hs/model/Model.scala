package hs.model

import hs.repository.Repository
import hs.entity.{Assignment, Course, Grade, Student}

import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

class Model(repository: Repository) {
  import repository._

  val studentList = ObservableBuffer[Student]()
  val selectedStudent = new ObjectProperty[Student]()

  def update(selectedIndex: Int, student: Student): Unit = {
    await(students.save(student))
    studentList.update(selectedIndex, student)
  }

  def add(student: Student): Unit = {
    val newId = await(students.save(student))
    val newStudent = student.copy(id = newId.get)
    studentList += newStudent
    selectedStudent.value = newStudent
  }

  val gradeList = ObservableBuffer[Grade]()
  val selectedGrade = new ObjectProperty[Grade]()

  def update(selectedIndex: Int, grade: Grade): Unit = {
    await(grades.save(grade))
    gradeList.update(selectedIndex, grade)
  }

  def add(grade: Grade): Unit = {
    val newId = await(grades.save(grade))
    val newGrade = grade.copy(id = newId.get)
    gradeList += newGrade
    selectedGrade.value = newGrade
  }

  val courseList = ObservableBuffer[Course]()
  val selectedCourse = new ObjectProperty[Course]()

  def update(selectedIndex: Int, course: Course): Unit = {
    await(courses.save(course))
    courseList.update(selectedIndex, course)
  }

  def add(course: Course): Unit = {
    val newId = await(courses.save(course))
    val newCourse = course.copy(id = newId.get)
    courseList += newCourse
    selectedCourse.value = newCourse
  }

  val assignmentList = ObservableBuffer[Assignment]()
  val selectedAssignment = new ObjectProperty[Assignment]()

  def update(selectedIndex: Int, assignment: Assignment): Unit = {
    await(assignments.save(assignment))
    assignmentList.update(selectedIndex, assignment)
  }

  def add(assignment: Assignment): Unit = {
    val newId = await(assignments.save(assignment))
    val newAssignment = assignment.copy(id = newId.get)
    assignmentList += newAssignment
    selectedAssignment.value = newAssignment
  }
}