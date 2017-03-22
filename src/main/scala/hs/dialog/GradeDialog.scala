package hs.dialog

import hs.App
import hs.repository.Grade

import scalafx.scene.control.Dialog

class GradeDialog(grade: Grade) extends Dialog[Grade]() {
  initOwner(App.stage)
  title = "Grade"
  headerText = "Save Grade"
}