package objektwerks.hs.dialog

import com.typesafe.config.Config
import objektwerks.hs.App
import objektwerks.hs.entity.Course
import objektwerks.hs.pane.ComponentGridPane

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.Region

class CourseDialog(conf: Config, course: Course) extends Dialog[Course]() {
  val saveButtonType = new ButtonType(conf.getString("save"), ButtonData.OKDone)
  val nameTextField = new TextField { text = course.name}
  val components = Map[String, Region](conf.getString("name") -> nameTextField)
  val componentGridPane = new ComponentGridPane(components)
  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = componentGridPane

  initOwner(App.stage)
  title = conf.getString("course")
  headerText = conf.getString("save-course")

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