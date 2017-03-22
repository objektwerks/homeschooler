package hs.domain

import java.time.LocalDate

case class Assignment(id: Int = 0, courseid: Int, task: String, assigned: LocalDate = LocalDate.now, completed: LocalDate = LocalDate.now, score: Double = 0.0)