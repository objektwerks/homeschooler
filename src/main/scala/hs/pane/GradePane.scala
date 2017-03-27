package hs.pane

import hs.{Model, Store}
import hs.dialog.GradeDialog
import hs.repository.Grade

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class GradePane() extends HBox {
  val gradeLabel = new Label { text = "Grade:" }
  val gradeCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Grade](g => g.year) )
  val gradeComboBox = new ComboBox[Grade] { prefHeight = 25; prefWidth = 203; items = Model.grades; cellFactory = gradeCellFactory }
  val gradePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val gradeAddButton = new Button { text = "+"; prefHeight = 25; disable = true }

  spacing = 6
  children = List(gradeLabel, gradeComboBox, gradePropsButton, gradeAddButton)

  Model.selectedGrade <== gradeComboBox.value
  gradeComboBox.selectionModel().selectedItemProperty().onChange { (_, _, _) =>
    gradePropsButton.disable = false
    gradeAddButton.disable = false
  }
  gradePropsButton.onAction = { _ => save(gradeComboBox.value.value) }
  gradeAddButton.onAction = { _ => save(Grade(studentid = Model.selectedStudent.value.id)) }

  def save(grade: Grade): Unit = {
    val result = new GradeDialog(grade).showAndWait()
    result match {
      case Some(Grade(id, studentid, year, started, completed)) =>
        import Store.repository._
        val grade = Grade(id, studentid, year, started, completed)
        val gradeId = await(grades.save(grade))
        val persistedGrade = grade.copy(id = gradeId.get)
        if (id == 0) {
          Model.grades += persistedGrade
          gradeComboBox.value = persistedGrade
        }
      case _ => println("Grade dialog failed!")
    }
  }
}