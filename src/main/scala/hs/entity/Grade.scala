package hs.entity

import java.time.LocalDate

case class Grade(id: Int = 0, studentid: Int, year: String, started: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now.plusMonths(6))