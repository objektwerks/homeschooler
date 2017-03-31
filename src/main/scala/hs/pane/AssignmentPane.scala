package hs.pane

import java.time.format.DateTimeFormatter

import com.typesafe.config.Config
import hs.dialog.AssignmentDialog
import hs.entity.Assignment
import hs.model.Model

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentPane(conf: Config, model: Model) extends VBox {
  val assignmentLabel = new Label { text = "Assignments:" }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentListView = new ListView[Assignment] { prefWidth = 333; items = model.assignmentList; cellFactory = assignmentCellFactory
                                                      selectionModel().selectionMode = SelectionMode.Single }
  val assignedDate = new Label { text = "00/00/0000" }
  val toLabel = new Label { text = " - " }
  val completedDate = new Label { text = "00/00/0000" }
  val scoreLabel = new Label { text = "0" }
  val splitLabel = new Label { text = " / " }
  val totalLabel = new Label { text = "0" }
  val assignmentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val assignmentAddButton = new Button { text = "+"; prefHeight = 25; disable = true }
  val assignmentToolBar = new HBox { spacing = 6; children = List(assignmentPropsButton, assignmentAddButton) }
  val assignmentDetailsPane = new HBox { spacing = 6; children = List(assignedDate, toLabel, completedDate, scoreLabel,
                                                                      splitLabel, totalLabel, assignmentToolBar) }

  spacing = 6
  children = List(assignmentLabel, assignmentListView, assignmentDetailsPane)

  model.selectedCourse.onChange { (_, _, selectedCourse) =>
    model.listAssignments(selectedCourse)
    assignmentAddButton.disable = false
  }

  assignmentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedAssignment) =>
    if (selectedAssignment != null) {
      model.selectedAssignment.value = selectedAssignment.id
      val dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd")
      assignedDate.text = selectedAssignment.assigned.format(dateTimeFormatter)
      completedDate.text = selectedAssignment.completed.format(dateTimeFormatter)
      scoreLabel.text = selectedAssignment.score.toInt.toString
      totalLabel.text = score(selectedAssignment)
      assignmentPropsButton.disable = false
    }
  }

  assignmentPropsButton.onAction = { _ => update(assignmentListView.selectionModel().getSelectedIndex, assignmentListView.selectionModel().getSelectedItem) }

  assignmentAddButton.onAction = { _ => add(Assignment(courseid = model.selectedCourse.value)) }

  def update(selectedIndex: Int, assignment: Assignment): Unit = {
    new AssignmentDialog(conf, assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        model.updateAssignment(selectedIndex, Assignment(id, courseid, task, assigned, completed, score))
        assignmentListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }

  def add(assignment: Assignment): Unit = {
    new AssignmentDialog(conf, assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        val newAssignment = model.addAssignment(Assignment(id, courseid, task, assigned, completed, score))
        assignmentListView.selectionModel().select(newAssignment)
      case _ =>
    }
  }

  def score(assignment: Assignment): String = model.scoreAssignments(assignment.courseid).toInt.toString
}