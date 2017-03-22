package hs.dialog

import hs.entity.Grade

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.Dialog

class GradeDialog(grade: Grade, stage: PrimaryStage) extends Dialog[Grade]() {
  initOwner(stage)
  title = "Grade"
  headerText = "Save Grade"
}