package objektwerks.hs.dialog

import com.typesafe.config.Config
import objektwerks.hs.App
import objektwerks.hs.entity.Student
import objektwerks.hs.pane.ComponentGridPane

import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.Region

class StudentDialog(conf: Config, student: Student) extends Dialog[Student]() {
  val saveButtonType = new ButtonType(conf.getString("save"), ButtonData.OKDone)
  val nameTextField = new TextField { text = student.name}
  val bornDatePicker = new DatePicker { value = student.born}
  val components = Map[String, Region](conf.getString("name") -> nameTextField, conf.getString("born") -> bornDatePicker)
  val componentGridPane = new ComponentGridPane(components)
  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = componentGridPane

  initOwner(App.stage)
  title = conf.getString("student")
  headerText = conf.getString("save-student")

  val saveButton = dialog.lookupButton(saveButtonType)
  saveButton.disable = nameTextField.text.value.trim.isEmpty
  nameTextField.text.onChange { (_, _, newValue) =>
    saveButton.disable = newValue.trim.isEmpty
  }

  resultConverter = dialogButton => {
    if (dialogButton == saveButtonType)
      student.copy(name = nameTextField.text.value, born = bornDatePicker.value.value)
    else null
  }
}