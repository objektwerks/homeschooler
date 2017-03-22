package hs.dialog

import hs.pane.DialogGridPane
import hs.repository.Student

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class StudentDialog(student: Student, stage: PrimaryStage) extends Dialog[Student]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val nameTextField = new TextField { promptText = "Name:"}
  val bornDatePicker = new DatePicker { promptText = "Born:"}
  val controls = Map[String, Control]("Name:" -> nameTextField, "Born:" -> bornDatePicker)
  val gridPane = new DialogGridPane(controls)

  dialogPane().getButtonTypes.addAll(saveButtonType, ButtonType.Cancel)
  dialogPane().contentProperty().set(gridPane)
  initOwner(stage)
  title = "Student"
  headerText = "Save Student"

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType) student.copy(name = nameTextField.text.value, born = bornDatePicker.value.value)
    else null
  }
}