package hs.pane

import com.typesafe.config.Config
import hs.dialog.GradeDialog
import hs.entity.Grade
import hs.model.Model
import hs.view.View

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class GradePane(conf: Config, model: Model) extends HBox {
  val gradeLabel = new Label { text = "Grade:" }
  val gradeCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Grade](g => g.year) )
  val gradeListView = new ListView[Grade] { prefHeight = 100; prefWidth = 203; items = model.gradeList; cellFactory = gradeCellFactory }
  val gradePropsButton = new Button { graphic = View.editImageView(); prefHeight = 25; disable = true }
  val gradeAddButton = new Button { graphic = View.addImageView(); prefHeight = 25; disable = true }

  spacing = 6
  children = List(gradeLabel, gradeListView, gradePropsButton, gradeAddButton)

  model.selectedStudent.onChange { (_, _, selectedStudent) =>
    model.listGrades(selectedStudent)
    gradeAddButton.disable = false
  }

  gradeListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedGrade) =>
    if (selectedGrade != null) {
      model.selectedGrade.value = selectedGrade.id
      gradePropsButton.disable = false
    }
  }

  gradePropsButton.onAction = { _ => update(gradeListView.selectionModel().getSelectedIndex, gradeListView.selectionModel().getSelectedItem) }

  gradeAddButton.onAction = { _ => add(Grade(studentid = model.selectedStudent.value)) }

  def update(selectedIndex: Int, grade: Grade): Unit = {
    new GradeDialog(conf, grade).showAndWait() match {
      case Some(Grade(id, studentid, year, started, completed)) =>
        model.updateGrade(selectedIndex, Grade(id, studentid, year, started, completed))
        gradeListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }

  def add(grade: Grade): Unit = {
    new GradeDialog(conf, grade).showAndWait() match {
      case Some(Grade(id, studentid, year, started, completed)) =>
        val newGrade = model.addGrade(Grade(id, studentid, year, started, completed))
        gradeListView.selectionModel().select(newGrade)
      case _ =>
    }
  }
}