package hs.dialog

import hs.App
import hs.pane.ControlGridPane
import hs.repository.Course

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class CourseDialog(course: Course) extends Dialog[Course]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val nameTextField = new TextField { text = course.name}
  val gridPaneControls = Map[String, Control]("Name:" -> nameTextField)
  val gridPane = new ControlGridPane(gridPaneControls)

  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = gridPane
  initOwner(App.stage)
  title = "Course"
  headerText = "Save Course"

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = nameTextField.text.value.trim.isEmpty
  nameTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      course.copy(name = nameTextField.text.value)
    else null
  }
}