package hs

import hs.repository.{Assignment, Course, Grade, Student}
import hs.Store.repository._

import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

object Model {
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

  val assignmentList = ObservableBuffer[Assignment]()
  val selectedAssignment = new ObjectProperty[Assignment]()
}