package hs

import hs.repository.{Assignment, Course, Grade, Student}

import scalafx.collections.ObservableBuffer

object Model {
  val students = ObservableBuffer[Student]()
  val grades = ObservableBuffer[Grade]()
  val courses = ObservableBuffer[Course]()
  val assignments = ObservableBuffer[Assignment]()
}