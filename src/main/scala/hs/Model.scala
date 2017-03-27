package hs

import hs.repository.{Assignment, Course, Grade, Student}

import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

object Model {
  val students = ObservableBuffer[Student]()
  val grades = ObservableBuffer[Grade]()
  val courses = ObservableBuffer[Course]()
  val assignments = ObservableBuffer[Assignment]()

  val selectedStudent = new ObjectProperty[Student]()
  val selectedGrade = new ObjectProperty[Grade]()
  val selectedCourse = new ObjectProperty[Course]()
  val selectedAssignment = new ObjectProperty[Assignment]()
}