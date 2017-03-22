package hs.dialog

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class GradeDialog[Grade](stage: PrimaryStage) extends Dialog[Grade]() {
  initOwner(stage)
  title = "Grade"
  headerText = "Save Grade"
}