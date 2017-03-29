package hs.dialog

import com.typesafe.config.Config
import hs.App
import hs.entity.Grade
import hs.pane.ControlGridPane

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class GradeDialog(conf: Config, grade: Grade) extends Dialog[Grade]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val yearTextField = new TextField { text = grade.year }
  val startedDatePicker = new DatePicker { value = grade.started }
  val completedDatePicker = new DatePicker { value = grade.completed }
  val gridPaneControls = Map[String, Control](
    "Year:" -> yearTextField,
    "Started:" -> startedDatePicker,
    "Completed:" -> completedDatePicker)
  val gridPane = new ControlGridPane(gridPaneControls)

  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = gridPane
  initOwner(App.stage)
  title = "Grade"
  headerText = "Save Grade"

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = yearTextField.text.value.trim.isEmpty
  yearTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      grade.copy(
        year = yearTextField.text.value,
        started = startedDatePicker.value.value,
        completed = completedDatePicker.value.value)
    else null
  }
}