package hs.dialog

import hs.App
import hs.repository.Course

import scalafx.scene.control.Dialog

class CourseDialog(course: Course) extends Dialog[Course]() {
  initOwner(App.stage)
  title = "Course"
  headerText = "Save Course"
}