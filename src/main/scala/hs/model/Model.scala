package hs.model

import hs.entity.{Assignment, Course, Grade, Student}
import hs.repository.Repository

import scala.collection.JavaConverters._
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer

class Model(repository: Repository) {
  import repository._

  val studentList = ObservableBuffer[Student]()
  var selectedStudent = ObjectProperty[Student](Student())

  def listStudents(): Unit = {
    studentList.clear()
    gradeList.clear()
    courseList.clear()
    assignmentList.clear()
    studentList.addAll( await(students.list()).asJava )
  }

  def updateStudent(selectedIndex: Int, student: Student): Unit = {
    await(students.save(student))
    studentList.update(selectedIndex, student)
  }

  def addStudent(student: Student): Unit = {
    val newId = await(students.save(student))
    val newStudent = student.copy(id = newId.get)
    studentList += newStudent
    selectedStudent = ObjectProperty[Student](newStudent)
  }

  val gradeList = ObservableBuffer[Grade]()
  var selectedGrade = ObjectProperty[Grade](Grade(studentid = 0))

  def listGrades(studentId: Int): Unit = {
    gradeList.clear()
    courseList.clear()
    assignmentList.clear()
    gradeList.addAll( await(grades.list(studentId)).asJava )
  }

  def updateGrade(selectedIndex: Int, grade: Grade): Unit = {
    await(grades.save(grade))
    gradeList.update(selectedIndex, grade)
  }

  def addGrade(grade: Grade): Unit = {
    val newId = await(grades.save(grade))
    val newGrade = grade.copy(id = newId.get)
    gradeList += newGrade
    selectedGrade = ObjectProperty[Grade](newGrade)
  }

  val courseList = ObservableBuffer[Course]()
  var selectedCourse = ObjectProperty[Course](Course(gradeid = 0))

  def listCourses(gradeId: Int): Unit = {
    courseList.clear()
    assignmentList.clear()
    courseList.addAll( await(courses.list(gradeId)).asJava )
  }

  def updateCourse(selectedIndex: Int, course: Course): Unit = {
    await(courses.save(course))
    courseList.update(selectedIndex, course)
  }

  def addCourse(course: Course): Unit = {
    val newId = await(courses.save(course))
    val newCourse = course.copy(id = newId.get)
    courseList += newCourse
    selectedCourse = ObjectProperty[Course](newCourse)
  }

  val assignmentList = ObservableBuffer[Assignment]()
  var selectedAssignment = ObjectProperty[Assignment](Assignment(courseid = 0))

  def listAssignments(courseId: Int): Unit = {
    assignmentList.clear()
    assignmentList.addAll( await(assignments.list(courseId)).asJava )
  }

  def updateAssignment(selectedIndex: Int, assignment: Assignment): Unit = {
    await(assignments.save(assignment))
    assignmentList.update(selectedIndex, assignment)
  }

  def addAssignment(assignment: Assignment): Unit = {
    val newId = await(assignments.save(assignment))
    val newAssignment = assignment.copy(id = newId.get)
    assignmentList += newAssignment
    selectedAssignment = ObjectProperty[Assignment](assignment)
  }

  def scoreAssignments(courseId: Int): Double = await(assignments.score(courseId)).getOrElse(0.0)
}