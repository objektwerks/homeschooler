package hs.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog, DatePicker, Label, Slider, TextField}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.{HBox, Region}

import hs.{App, Assignment, Context, Entity}
import hs.pane.ControlGridPane

class AssignmentDialog(context: Context, assignment: Assignment) extends Dialog[Assignment]:
  val taskTextField = new TextField:
    text = assignment.task

  val assignedDatePicker = new DatePicker:
    value = Entity.toLocalDate(assignment.assigned)

  val completedDatePicker = new DatePicker:
    value = Entity.toLocalDate(assignment.completed)

  val scoreLabel = new Label:
    text = assignment.score.toInt.toString

  val scoreSlider = new Slider:
    min = 50.0
    max = 100.00
    value = assignment.score; showTickLabels = true

  val scoreBox = new HBox:
    children = List(scoreSlider, scoreLabel)

  val controls = List[(String, Region)](
    context.task -> taskTextField,
    context.assigned -> assignedDatePicker,
    context.completed -> completedDatePicker,
    context.score -> scoreBox)
  val controlGridPane = ControlGridPane(controls)

  val dialog = dialogPane()
  val saveButtonType = ButtonType(context.save, ButtonData.OKDone)
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = controlGridPane

  scoreSlider.value.onChange { (_, _, newScore) => scoreLabel.text = newScore.intValue.toString }

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = taskTextField.text.value.trim.isEmpty
  taskTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton =>
    if (dialogButton == saveButtonType) then
      assignment.copy(
        task = taskTextField.text.value,
        assigned = assignedDatePicker.value.value.toString,
        completed = completedDatePicker.value.value.toString,
        score = scoreSlider.value.value
      )
    else null

  initOwner(App.stage)
  title = context.assignment
  headerText = context.saveAssignement