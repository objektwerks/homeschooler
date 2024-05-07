package hs.dialog

import com.typesafe.config.Config

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.Region

import hs.{App, Course, Entity}
import hs.pane.ControlGridPane

class CourseDialog(conf: Config, course: Course) extends Dialog[Course]:
  val nameTextField = new TextField:
    text = course.name

  val startedDatePicker = new DatePicker:
    value = Entity.toLocalDate(course.started)

  val completedDatePicker = new DatePicker:
    value = Entity.toLocalDate(course.completed)

  val controls = List[(String, Region)](
    conf.getString("name") -> nameTextField,
    conf.getString("started") -> startedDatePicker,
    conf.getString("completed") -> completedDatePicker
  )
  val controlGridPane = new ControlGridPane(controls)

  val dialog = dialogPane()
  val saveButtonType = new ButtonType(conf.getString("save"), ButtonData.OKDone)
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = controlGridPane

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = nameTextField.text.value.trim.isEmpty
  nameTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton =>
    if (dialogButton == saveButtonType)
      course.copy(name = nameTextField.text.value,
        started = startedDatePicker.value.value.toString,
        completed = completedDatePicker.value.value.toString)
    else null

  initOwner(App.stage)
  title = conf.getString("course")
  headerText = conf.getString("save-course")