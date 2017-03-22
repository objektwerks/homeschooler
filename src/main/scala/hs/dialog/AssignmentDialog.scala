package hs.dialog

import hs.domain.Assignment

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class AssignmentDialog(assignment: Assignment, stage: PrimaryStage) extends Dialog[Assignment]() {
  initOwner(stage)
  title = "Assignment"
  headerText = "Save Assignment"
}