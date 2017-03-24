package hs.dialog

import hs.App
import hs.pane.ControlGridPane
import hs.repository.Course

import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._

class CourseDialog(course: Course) extends Dialog[Course]() {
  val saveButtonType = new ButtonType("Save", ButtonData.OKDone)
  val nameTextField = new TextField { text = course.name}
  val gridPaneControls = Map[String, Control]("Name:" -> nameTextField)
  val gridPane = new ControlGridPane(gridPaneControls)

  dialogPane().getButtonTypes.addAll(saveButtonType, ButtonType.Cancel)
  dialogPane().contentProperty().set(gridPane)
  initOwner(App.stage)
  title = "Course"
  headerText = "Save Course"

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      course.copy(name = nameTextField.text.value)
    else null
  }
}