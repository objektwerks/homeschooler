package hs.dialog

import hs.domain.Student

import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.GridPane

class StudentDialog(student: Student, stage: PrimaryStage) extends Dialog[Student]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val nameTextField = new TextField { promptText = "Name:"}
  val bornDatePicker = new DatePicker { promptText = "Born:"}
  val grid = new GridPane() {
    hgap = 10
    vgap = 10
    padding = Insets(20, 100, 10, 10)
    add(new Label("Name:"), 0, 0)
    add(nameTextField, 1, 0)
    add(new Label("Born:"), 0, 1)
    add(bornDatePicker, 1, 1)
  }
  resultConverter = dialogButton =>
    if (dialogButton == saveButtonType) student.copy(name = nameTextField.text.value, born = bornDatePicker.value.value) else student

  dialogPane().getButtonTypes.addAll(saveButtonType, ButtonType.Cancel)
  dialogPane().contentProperty().set(grid)
  initOwner(stage)
  title = "Student"
  headerText = "Save Student"
}