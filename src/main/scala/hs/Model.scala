package hs

import Entity._
import Repository._

import scalafx.beans.property.IntegerProperty
import scalafx.collections.ObservableBuffer

object Model {
  def apply(repository: Repository): Model = new Model(repository)
}

class Model(repository: Repository) {
  import repository._

  val studentList = ObservableBuffer[Student]()
  val selectedStudentId = IntegerProperty(0)

  val gradeList = ObservableBuffer[Grade]()
  val selectedGradeId = IntegerProperty(0)

  val courseList = ObservableBuffer[Course]()
  val selectedCourseId = IntegerProperty(0)
  
  val assignmentList = ObservableBuffer[Assignment]()
  val selectedAssignmentId = IntegerProperty(0)

  def listStudents(): Unit = {
    studentList.clear()
    gradeList.clear()
    courseList.clear()
    assignmentList.clear()
    studentList ++= await(students.list())
  }

  def addStudent(student: Student): Student = {
    val newId = await(students.save(student))
    val newStudent = student.copy(id = newId.get)
    studentList += newStudent
    selectedStudentId.value = newStudent.id
    newStudent
  }

  def updateStudent(selectedIndex: Int, student: Student): Unit = {
    await(students.save(student))
    studentList.update(selectedIndex, student)
    studentList.sorted
    ()
  }

  def listGrades(studentId: Int): Unit = {
    gradeList.clear()
    courseList.clear()
    assignmentList.clear()
    gradeList ++= await(grades.list(studentId))
  }

  def addGrade(grade: Grade): Grade = {
    val newId = await(grades.save(grade))
    val newGrade = grade.copy(id = newId.get)
    gradeList += newGrade
    selectedGradeId.value = newGrade.id
    newGrade
  }

  def updateGrade(selectedIndex: Int, grade: Grade): Unit = {
    await(grades.save(grade))
    gradeList.update(selectedIndex, grade)
    gradeList.sorted
    ()
  }

  def listCourses(gradeId: Int): Unit = {
    courseList.clear()
    assignmentList.clear()
    courseList ++= await(courses.list(gradeId))
  }

  def addCourse(course: Course): Course = {
    val newId = await(courses.save(course))
    val newCourse = course.copy(id = newId.get)
    courseList += newCourse
    selectedCourseId.value = newCourse.id
    newCourse
  }

  def updateCourse(selectedIndex: Int, course: Course): Unit = {
    await(courses.save(course))
    courseList.update(selectedIndex, course)
    courseList.sorted
    ()
  }

  def listAssignments(courseId: Int): Unit = {
    assignmentList.clear()
    assignmentList ++= await(assignments.list(courseId))
  }

  def addAssignment(assignment: Assignment): Assignment = {
    val newId = await(assignments.save(assignment))
    val newAssignment = assignment.copy(id = newId.get)
    assignmentList += newAssignment
    selectedAssignmentId.value = newAssignment.id
    newAssignment
  }

  def updateAssignment(selectedIndex: Int, assignment: Assignment): Unit = {
    await(assignments.save(assignment))
    assignmentList.update(selectedIndex, assignment)
    assignmentList.sorted
    ()
  }

  def scoreCourse(courseId: Int): Double = await(assignments.score(courseId)).getOrElse(0.0)
}