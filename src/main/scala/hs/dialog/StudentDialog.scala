package hs.dialog

import hs.App
import hs.pane.ControlGridPane
import hs.repository.Student

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class StudentDialog(student: Student) extends Dialog[Student]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val nameTextField = new TextField { text = student.name}
  val bornDatePicker = new DatePicker { value = student.born}
  val gridPaneControls = Map[String, Control]("Name:" -> nameTextField, "Born:" -> bornDatePicker)
  val gridPane = new ControlGridPane(gridPaneControls)

  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = gridPane
  initOwner(App.stage)
  title = "Student"
  headerText = "Save Student"

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      student.copy(name = nameTextField.text.value, born = bornDatePicker.value.value)
    else null
  }
}