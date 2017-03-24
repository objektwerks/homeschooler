package hs.dialog

import hs.App
import hs.pane.ControlGridPane
import hs.repository.Grade

import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class GradeDialog(grade: Grade) extends Dialog[Grade]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val yearTextField = new TextField { text = grade.year }
  val startedDatePicker = new DatePicker { value = grade.started }
  val completedDatePicker = new DatePicker { value = grade.completed }
  val gridPaneControls = Map[String, Control]("Year:" -> yearTextField, "Started:" -> startedDatePicker, "Completed:" -> completedDatePicker)
  val gridPane = new ControlGridPane(gridPaneControls)

  dialogPane().getButtonTypes.addAll(saveButtonType, ButtonType.Cancel)
  dialogPane().contentProperty().set(gridPane)
  initOwner(App.stage)
  title = "Grade"
  headerText = "Save Grade"

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      grade.copy(year = yearTextField.text.value, started = startedDatePicker.value.value, completed = completedDatePicker.value.value)
    else null
  }
}