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
                         name: String = "",
                         born: String = LocalDate.now.minusYears(7).toString) extends Entity

final case class Grade(id: Int = 0,
                       studentid: Int,
                       year: String = "",
                       started: String = LocalDate.now.toString,
                       completed: String = LocalDate.now.plusMonths(6).toString) extends Entity

final case class Course(id: Int = 0,
                        gradeid: Int,
                        name: String = "",
                        started: String = LocalDate.now.toString,
                        completed: String = LocalDate.now.plusMonths(3).toString) extends Entity

final case class Assignment(id: Int = 0,
                            courseid: Int,
                            task: String = "",
                            assigned: String = LocalDate.now.toString,
                            completed: String = LocalDate.now.toString,
                            score: Double = 50.0) extends Entity