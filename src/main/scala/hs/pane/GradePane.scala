package hs.pane

import com.typesafe.config.Config
import hs.dialog.GradeDialog
import hs.entity.Grade
import hs.model.Model

import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class GradePane(conf: Config, model: Model) extends HBox {
  val gradeLabel = new Label { text = "Grade:" }
  val gradeCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Grade](g => g.year) )
  val gradeComboBox = new ComboBox[Grade] { prefHeight = 25; prefWidth = 203; items = model.gradeList; cellFactory = gradeCellFactory }
  val gradePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val gradeAddButton = new Button { text = "+"; prefHeight = 25; disable = true }

  spacing = 6
  children = List(gradeLabel, gradeComboBox, gradePropsButton, gradeAddButton)

  model.selectedGrade <== gradeComboBox.selectionModel().selectedItemProperty()

  model.selectedGrade.onChange { (_, _, selectedGrade) =>
    gradePropsButton.disable = false
    gradeAddButton.disable = false
    gradeComboBox.selectionModel().select(selectedGrade)
  }

  gradePropsButton.onAction = { _ => update(gradeComboBox.selectionModel().getSelectedIndex, gradeComboBox.selectionModel().getSelectedItem) }

  gradeAddButton.onAction = { _ => add(Grade(studentid = model.selectedStudent.value.id)) }

  def update(selectedIndex: Int, grade: Grade): Unit = {
    new GradeDialog(grade).showAndWait() match {
      case Some(Grade(id, studentid, year, started, completed)) => model.update(selectedIndex, Grade(id, studentid, year, started, completed))
      case _ =>
    }
  }

  def add(grade: Grade): Unit = {
    new GradeDialog(grade).showAndWait() match {
      case Some(Grade(id, studentid, year, started, completed)) => model.add(Grade(id, studentid, year, started, completed))
      case _ =>
    }
  }
}