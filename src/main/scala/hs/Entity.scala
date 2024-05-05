package hs

import java.time.LocalDate

sealed trait Entity

object Entity {
  implicit def studentOrdering: Ordering[Student] = Ordering.by(_.born)
  implicit def gradeOrdering: Ordering[Grade] = Ordering.by(_.started)
  implicit def courseOrdering: Ordering[Course] = Ordering.by(_.started)
  implicit def assignmentOrdering: Ordering[Assignment] = Ordering.by(_.assigned)
}

final case class Student(id: Int = 0,
                         name: String = "default_student",
                         born: LocalDate = LocalDate.now.minusYears(7)) extends Entity

final case class Grade(id: Int = 0,
                       studentid: Int,
                       year: String = "default_grade",
                       started: LocalDate = LocalDate.now,
                       completed: LocalDate = LocalDate.now.plusMonths(6)) extends Entity

final case class Course(id: Int = 0,
                        gradeid: Int,
                        name: String = "default_course",
                        started: LocalDate = LocalDate.now,
                        completed: LocalDate = LocalDate.now.plusMonths(3)) extends Entity

final case class Assignment(id: Int = 0,
                            courseid: Int,
                            task: String = "default_assignment",
                            assigned: String = LocalDate.now.toString,
                            completed: String = LocalDate.now.toString,
                            score: Double = 50.0) extends Entity