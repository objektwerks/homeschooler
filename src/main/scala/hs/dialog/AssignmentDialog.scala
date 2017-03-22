package hs.dialog

import hs.App
import hs.repository.Assignment

import scalafx.scene.control.Dialog

class AssignmentDialog(assignment: Assignment) extends Dialog[Assignment]() {
  initOwner(App.stage)
  title = "Assignment"
  headerText = "Save Assignment"
}