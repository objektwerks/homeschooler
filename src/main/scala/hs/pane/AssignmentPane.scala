package hs.pane

import hs.Model
import hs.dialog.AssignmentDialog
import hs.repository.Assignment

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentPane() extends VBox {
  val assignmentLabel = new Label { text = "Assignments:" }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentList = new ListView[Assignment] { prefWidth = 333; items = Model.assignmentList; cellFactory = assignmentCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val assignedDate = new Label { text = "00/00/0000" }
  val toLabel = new Label { text = " - " }
  val completedDate = new Label { text = "00/00/0000" }
  val scoreLabel = new Label { text = "0.0" }
  val splitLabel = new Label { text = " / " }
  val totalLabel = new Label { text = "0.0" }
  val assignmentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val assignmentAddButton = new Button { text = "+"; prefHeight = 25; disable = true }
  val assignmentToolBar = new HBox { spacing = 6; children = List(assignmentPropsButton, assignmentAddButton) }
  val assignmentDetailsPane = new HBox { spacing = 6; children = List(assignedDate, toLabel, completedDate, scoreLabel, splitLabel, totalLabel, assignmentToolBar) }

  spacing = 6
  children = List(assignmentLabel, assignmentList, assignmentDetailsPane)

  Model.selectedAssignment <== assignmentList.selectionModel().selectedItemProperty()

  Model.selectedAssignment.onChange { (_, _, selectedAssignment) =>
    assignmentPropsButton.disable = false
    assignmentAddButton.disable = false
    assignmentList.selectionModel().select(selectedAssignment)
  }

  assignmentPropsButton.onAction = { _ => update(assignmentList.selectionModel().getSelectedIndex, assignmentList.selectionModel().getSelectedItem) }

  assignmentAddButton.onAction = { _ => add(Assignment(courseid = Model.selectedCourse.value.id)) }

  def update(selectedIndex: Int, assignment: Assignment): Unit = {
    new AssignmentDialog(assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) => Model.update(selectedIndex, Assignment(id, courseid, task, assigned, completed, score))
      case _ =>
    }
  }

  def add(assignment: Assignment): Unit = {
    new AssignmentDialog(assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) => Model.add(Assignment(id, courseid, task, assigned, completed, score))
      case _ =>
    }
  }
}