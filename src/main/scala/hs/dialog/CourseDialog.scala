package hs.dialog

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class CourseDialog[Course](stage: PrimaryStage) extends Dialog[Course]() {
  initOwner(stage)
  title = "Course"
  headerText = "Save Course"
}