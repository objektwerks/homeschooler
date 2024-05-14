package hs.dialog

import scalafx.Includes.*
import scalafx.scene.control.{ButtonType, Dialog, DatePicker, TextField}
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.layout.Region

import hs.{App, Context, Course, Entity}
import hs.pane.ControlGridPane

class CourseDialog(context: Context, course: Course) extends Dialog[Course]:
  val nameTextField = new TextField:
    text = course.name

  val startedDatePicker = new DatePicker:
    value = Entity.toLocalDate(course.started)

  val completedDatePicker = new DatePicker:
    value = Entity.toLocalDate(course.completed)

  val controls = List[(String, Region)](
    context.name -> nameTextField,
    context.started -> startedDatePicker,
    context.completed -> completedDatePicker
  )
  val controlGridPane = ControlGridPane(controls)

  val dialog = dialogPane()
  val saveButtonType = new ButtonType(context.save, ButtonData.OKDone)
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = controlGridPane

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = nameTextField.text.value.trim.isEmpty
  nameTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton =>
    if (dialogButton == saveButtonType) then
      course.copy(
        name = nameTextField.text.value,
        started = startedDatePicker.value.value.toString,
        completed = completedDatePicker.value.value.toString
      )
    else null

  initOwner(App.stage)
  title = context.course
  headerText = context.saveCourse