package objektwerks.hs.pane

import com.typesafe.config.Config
import objektwerks.hs.dialog.GradeDialog
import objektwerks.hs.entity.Grade
import objektwerks.hs.image.Images
import objektwerks.hs.model.Model

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class GradePane(conf: Config, model: Model) extends VBox {
  val gradeLabel = new Label { text = conf.getString("grades") }
  val gradeCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Grade](g => g.year) )
  val gradeListView = new ListView[Grade] { minHeight = 50; items = model.gradeList; cellFactory = gradeCellFactory }
  val gradeAddButton = new Button { graphic = Images.addImageView(); prefHeight = 25; disable = true }
  val gradeEditButton = new Button { graphic = Images.editImageView(); prefHeight = 25; disable = true }
  val gradeToolBar = new HBox { spacing = 6; children = List(gradeAddButton, gradeEditButton) }

  spacing = 6
  children = List(gradeLabel, gradeListView, gradeToolBar)

  model.selectedStudentId.onChange { (_, _, selectedStudent) =>
    model.listGrades(selectedStudent)
    gradeAddButton.disable = false
  }

  gradeListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedGrade) =>
    if (selectedGrade != null) { // model.update yields a remove and add to items. the remove passes a null selectedGrade!
      model.selectedGradeId.value = selectedGrade.id
      gradeEditButton.disable = false
    }
  }

  gradeAddButton.onAction = { _ => add(Grade(studentid = model.selectedStudentId.value)) }

  gradeEditButton.onAction = { _ => update(gradeListView.selectionModel().getSelectedIndex,
                                            gradeListView.selectionModel().getSelectedItem) }

  def add(grade: Grade): Unit = {
    new GradeDialog(conf, grade).showAndWait() match {
      case Some(Grade(id, studentid, year, started, completed)) =>
        val newGrade = model.addGrade(Grade(id, studentid, year, started, completed))
        gradeListView.selectionModel().select(newGrade)
      case _ =>
    }
  }

  def update(selectedIndex: Int, grade: Grade): Unit = {
    new GradeDialog(conf, grade).showAndWait() match {
      case Some(Grade(id, studentid, year, started, completed)) =>
        model.updateGrade(selectedIndex, Grade(id, studentid, year, started, completed))
        gradeListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }
}