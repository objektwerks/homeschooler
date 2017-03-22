package hs.dialog

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class StudentDialog[Student](stage: PrimaryStage) extends Dialog[Student]() {
  initOwner(stage)
  title = "Student"
  headerText = "Save Student"
}