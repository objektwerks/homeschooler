package objektwerks.hs.dialog

import com.typesafe.config.Config
import objektwerks.hs.App
import objektwerks.hs.entity.Assignment
import objektwerks.hs.pane.ControlGridPane
import scalafx.Includes._
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, Region}

class AssignmentDialog(conf: Config, assignment: Assignment) extends Dialog[Assignment]() {
  val saveButtonType = new ButtonType(conf.getString("save"), ButtonData.OKDone)
  val taskTextField = new TextField { text = assignment.task }
  val assignedDatePicker = new DatePicker { value = assignment.assigned }
  val completedDatePicker = new DatePicker { value = assignment.completed }
  val scoreLabel = new Label { text = assignment.score.toInt.toString }
  val scoreSlider = new Slider { min = 50.0; max = 100.00; value = assignment.score; showTickLabels = true }
  val scoreBox = new HBox { children = List(scoreSlider, scoreLabel) }
  val controls = List[(String, Region)](
    conf.getString("task") -> taskTextField,
    conf.getString("assigned") -> assignedDatePicker,
    conf.getString("completed") -> completedDatePicker,
    conf.getString("score") -> scoreBox)
  val controlGridPane = new ControlGridPane(controls)
  val dialog = dialogPane()
  dialog.buttonTypes = List(saveButtonType, ButtonType.Cancel)
  dialog.content = controlGridPane

  initOwner(App.stage)
  title = conf.getString("assignment")
  headerText = conf.getString("save-assignment")

  scoreSlider.value.onChange { (_, _, newScore) => scoreLabel.text = newScore.intValue.toString }

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