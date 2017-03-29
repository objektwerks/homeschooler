package hs.dialog

import com.typesafe.config.Config
import hs.App
import hs.entity.Assignment
import hs.pane.ControlGridPane

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class AssignmentDialog(conf: Config, assignment: Assignment) extends Dialog[Assignment]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val taskTextField = new TextField { text = assignment.task }
  val assignedDatePicker = new DatePicker { value = assignment.assigned }
  val completedDatePicker = new DatePicker { value = assignment.completed }
  val scoreSlider = new Slider { min = 0.0; max = 100.00; value = assignment.score }
  val gridPaneControls = Map[String, Control](
    "Task:" -> taskTextField,
    "Assigned:" -> assignedDatePicker,
    "Completed:" -> completedDatePicker,
    "Score:" -> scoreSlider)
  val gridPane = new ControlGridPane(gridPaneControls)

  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = gridPane
  initOwner(App.stage)
  title = "Assignment"
  headerText = "Save Assignment"

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = taskTextField.text.value.trim.isEmpty
  taskTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      assignment.copy(
        task = taskTextField.text.value,
        assigned = assignedDatePicker.value.value,
        completed = completedDatePicker.value.value,
        score = scoreSlider.value.value)
    else null
  }
}