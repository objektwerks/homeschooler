package hs.dialog

import hs.App
import hs.pane.ControlGridPane
import hs.repository.Assignment

import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class AssignmentDialog(assignment: Assignment) extends Dialog[Assignment]() {
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

  dialogPane().getButtonTypes.addAll(saveButtonType, ButtonType.Cancel)
  dialogPane().contentProperty().set(gridPane)
  initOwner(App.stage)
  title = "Assignment"
  headerText = "Save Assignment"

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