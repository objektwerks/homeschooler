package hs.model

import hs.repository.Repository
import hs.entity.{Assignment, Course, Grade, Student}

import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

class Model(repository: Repository) {
  import repository._

  val studentList = ObservableBuffer[Student]()
  val selectedStudent = new ObjectProperty[Student]()

  def listStudents: Seq[Student] = await(students.list())

  def updateStudent(selectedIndex: Int, student: Student): Unit = {
    await(students.save(student))
    studentList.update(selectedIndex, student)
  }

  def addStudent(student: Student): Unit = {
    val newId = await(students.save(student))
    val newStudent = student.copy(id = newId.get)
    studentList += newStudent
    selectedStudent.value = newStudent
  }

  val gradeList = ObservableBuffer[Grade]()
  val selectedGrade = new ObjectProperty[Grade]()

  def listGrades(studentId: Int): Seq[Grade] = await(grades.list(studentId))

  def updateGrade(selectedIndex: Int, grade: Grade): Unit = {
    await(grades.save(grade))
    gradeList.update(selectedIndex, grade)
  }

  def addGrade(grade: Grade): Unit = {
    val newId = await(grades.save(grade))
    val newGrade = grade.copy(id = newId.get)
    gradeList += newGrade
    selectedGrade.value = newGrade
  }

  val courseList = ObservableBuffer[Course]()
  val selectedCourse = new ObjectProperty[Course]()

  def listCourses(gradeId: Int): Seq[Course] = await(courses.list(gradeId))

  def updateCourse(selectedIndex: Int, course: Course): Unit = {
    await(courses.save(course))
    courseList.update(selectedIndex, course)
  }

  def addCourse(course: Course): Unit = {
    val newId = await(courses.save(course))
    val newCourse = course.copy(id = newId.get)
    courseList += newCourse
    selectedCourse.value = newCourse
  }

  val assignmentList = ObservableBuffer[Assignment]()
  val selectedAssignment = new ObjectProperty[Assignment]()

  def listAssignments(courseId: Int): Seq[Assignment] = await(assignments.list(courseId))

  def updateAssignment(selectedIndex: Int, assignment: Assignment): Unit = {
    await(assignments.save(assignment))
    assignmentList.update(selectedIndex, assignment)
  }

  def addAssignment(assignment: Assignment): Unit = {
    val newId = await(assignments.save(assignment))
    val newAssignment = assignment.copy(id = newId.get)
    assignmentList += newAssignment
    selectedAssignment.value = newAssignment
  }

  def scoreAssignments(courseId: Int): Double = await(assignments.score(courseId)).getOrElse(0.0)
}