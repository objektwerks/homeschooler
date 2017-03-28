package hs

import hs.repository.{Assignment, Course, Grade, Student}
import hs.Store.repository._

import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

object Model {
  val studentList = ObservableBuffer[Student]()
  val gradeList = ObservableBuffer[Grade]()
  val courseList = ObservableBuffer[Course]()
  val assignmentList = ObservableBuffer[Assignment]()

  val selectedStudent = new ObjectProperty[Student]()
  val selectedGrade = new ObjectProperty[Grade]()
  val selectedCourse = new ObjectProperty[Course]()
  val selectedAssignment = new ObjectProperty[Assignment]()

  def update(selectedIndex: Int, student: Student): Unit = {
    await(students.save(student))
    studentList.update(selectedIndex, student)
  }

  def add(student: Student): Unit = {
    val newId = await(students.save(student))
    val newStudent = student.copy(id = newId.get)
    Model.studentList += newStudent
    Model.selectedStudent.value = newStudent
  }
}