package hs.pane

import hs.dialog.AssignmentDialog
import hs.repository.Assignment
import hs.{Model, Store}

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentPane() extends VBox {
  val assignmentLabel = new Label { text = "Assignments:" }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentList = new ListView[Assignment] { prefWidth = 333; items = Model.assignments; cellFactory = assignmentCellFactory; selectionModel().selectionMode = SelectionMode.Single }
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
  Model.selectedAssignment.onChange {
    assignmentPropsButton.disable = false
    assignmentAddButton.disable = false
  }
  assignmentPropsButton.onAction = { _ => save(assignmentList.selectionModel().getSelectedItem) }
  assignmentAddButton.onAction = { _ => save(Assignment(courseid = Model.selectedCourse.value.id)) }

  def save(assignment: Assignment): Unit = {
    val result = new AssignmentDialog(assignment).showAndWait()
    result match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        import Store.repository._
        val assignment = Assignment(id, courseid, task, assigned, completed, score)
        val assignmentId = await(assignments.save(assignment))
        val persistedAssignment = assignment.copy(id = assignmentId.get)
        if (id == 0) {
          Model.assignments += persistedAssignment
          assignmentList.selectionModel().select(persistedAssignment)
        }
      case _ => println("Assignment dialog failed!")
    }
  }
}