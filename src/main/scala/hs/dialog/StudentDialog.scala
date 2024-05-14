package hs.dialog

import scalafx.Includes.*
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, Dialog, DatePicker, TextField}
import scalafx.scene.layout.Region

import hs.{App, Context, Entity, Student}
import hs.pane.ControlGridPane

class StudentDialog(context: Context, student: Student) extends Dialog[Student]:
  val nameTextField = new TextField:
    text = student.name

  val bornDatePicker = new DatePicker:
    value = Entity.toLocalDate(student.born)

  val controls = List[(String, Region)](
    context.name -> nameTextField,
    context.born -> bornDatePicker
  )
  val controlGridPane = ControlGridPane(controls)

  val dialog = dialogPane()
  val saveButtonType = ButtonType(context.save, ButtonData.OKDone)
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = controlGridPane

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = nameTextField.text.value.trim.isEmpty
  nameTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton =>
    if (dialogButton == saveButtonType) then
      student.copy(
        name = nameTextField.text.value,
        born = bornDatePicker.value.value.toString
      )
    else null

  initOwner(App.stage)
  title = context.student
  headerText = context.saveStudent