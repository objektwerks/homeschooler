package hs

import java.time.LocalDate

sealed trait Entity extends Product with Serializable

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
                            assigned: LocalDate = LocalDate.now,
                            completed: LocalDate = LocalDate.now,
                            score: Double = 50.0) extends Entity

object Entity {
  implicit def localDateOrdering: Ordering[LocalDate] = Ordering.by(_.toEpochDay)
  implicit def studentOrdering: Ordering[Student] = Ordering.by(_.born)
  implicit def gradeOrdering: Ordering[Grade] = Ordering.by(_.started)
  implicit def courseOrdering: Ordering[Course] = Ordering.by(_.started)
  implicit def assignmentOrdering: Ordering[Assignment] = Ordering.by(_.assigned)
}