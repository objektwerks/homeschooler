package hs.dialog

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class AssignmentDialog[Assignment](stage: PrimaryStage) extends Dialog[Assignment]() {
  initOwner(stage)
  title = "Assignment"
  headerText = "Save Assignment"
}