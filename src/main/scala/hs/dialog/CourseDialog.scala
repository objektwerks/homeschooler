package hs.dialog

import hs.entity.Course

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class CourseDialog(course: Course, stage: PrimaryStage) extends Dialog[Course]() {
  initOwner(stage)
  title = "Course"
  headerText = "Save Course"
}