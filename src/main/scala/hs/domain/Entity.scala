package hs.domain

import java.time.LocalDate

case class Student(id: Int = 0, name: String, born: LocalDate)
case class Grade(id: Int = 0, studentid: Int, year: String, started: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now.plusMonths(6))
case class Course(id: Int = 0, gradeid: Int, name: String)
case class Assignment(id: Int = 0, courseid: Int, task: String, assigned: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now, score: Double = 0.0)