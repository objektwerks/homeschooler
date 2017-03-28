package hs.entity

import java.time.LocalDate

case class Student(id: Int = 0, name: String = "Name?", born: LocalDate = LocalDate.now.minusYears(7))
case class Grade(id: Int = 0, studentid: Int, year: String = "Year?", started: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now.plusMonths(6))
case class Course(id: Int = 0, gradeid: Int, name: String = "Name?")
case class Assignment(id: Int = 0, courseid: Int, task: String = "Task?", assigned: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now, score: Double = 0.0)