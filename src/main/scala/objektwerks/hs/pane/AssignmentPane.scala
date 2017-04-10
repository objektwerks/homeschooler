package objektwerks.hs.pane

import com.typesafe.config.Config
import objektwerks.hs.dialog.AssignmentDialog
import objektwerks.hs.entity.Assignment
import objektwerks.hs.image.Images
import objektwerks.hs.model.Model

import scalafx.Includes._
import scalafx.scene.control._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentPane(conf: Config, model: Model) extends VBox {
  val assignmentLabel = new Label { text = conf.getString("assignments") }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentListView = new ListView[Assignment] { minHeight = 300; items = model.assignmentList; cellFactory = assignmentCellFactory
                                                      selectionModel().selectionMode = SelectionMode.Single }
  val assignmentAddButton = new Button { graphic = Images.addImageView(); prefHeight = 25; disable = true }
  val assignmentEditButton = new Button { graphic = Images.editImageView(); prefHeight = 25; disable = true }
  val assignmentChartButton = new Button { graphic = Images.chartImageView(); prefHeight = 25; disable = true }
  val assignmentToolBar = new HBox { spacing = 6; children = List(assignmentAddButton, assignmentEditButton, assignmentChartButton) }

  spacing = 6
  children = List(assignmentLabel, assignmentListView, assignmentToolBar)

  model.selectedCourseId.onChange { (_, _, selectedCourse) =>
    model.listAssignments(selectedCourse)
    assignmentAddButton.disable = false
  }

  assignmentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedAssignment) =>
    if (selectedAssignment != null) { // model.update yields a remove and add to items. the remove passes a null selectedAssignment!
      model.selectedAssignmentId.value = selectedAssignment.id
      assignmentEditButton.disable = false
      assignmentChartButton.disable = false
    }
  }

  assignmentAddButton.onAction = { _ => add(Assignment(courseid = model.selectedCourseId.value)) }

  assignmentEditButton.onAction = { _ => update(assignmentListView.selectionModel().getSelectedIndex,
                                                 assignmentListView.selectionModel().getSelectedItem) }

  def add(assignment: Assignment): Unit = {
    new AssignmentDialog(conf, assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        val newAssignment = model.addAssignment(Assignment(id, courseid, task, assigned, completed, score))
        assignmentListView.selectionModel().select(newAssignment)
      case _ =>
    }
  }

  def update(selectedIndex: Int, assignment: Assignment): Unit = {
    new AssignmentDialog(conf, assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        model.updateAssignment(selectedIndex, Assignment(id, courseid, task, assigned, completed, score))
        assignmentListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }
}