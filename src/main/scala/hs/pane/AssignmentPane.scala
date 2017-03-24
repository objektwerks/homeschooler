package hs.pane

import hs.repository.Assignment

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentPane extends VBox {
  val assignmentLabel = new Label { text = "Assignments:" }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentList = new ListView[Assignment] { prefWidth = 333; items = ObservableBuffer[Assignment](); cellFactory = assignmentCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val assignedDate = new Label { text = "00/00/0000" }
  val toLabel = new Label { text = " - " }
  val completedDate = new Label { text = "00/00/0000" }
  val scoreLabel = new Label { text = "0.0" }
  val splitLabel = new Label { text = " / " }
  val totalLabel = new Label { text = "0.0" }
  val assignmentPropsButton = new Button { text = "*"; prefHeight = 25; onAction = { _ =>  } }
  val assignmentAddButton = new Button { text = "+"; prefHeight = 25; onAction = { _ =>  } }
  val assignmentToolBar = new HBox { spacing = 6; children = List(assignmentPropsButton, assignmentAddButton) }
  val assignmentDetailsPane = new HBox { spacing = 6; children = List(assignedDate, toLabel, completedDate, scoreLabel, splitLabel, totalLabel, assignmentToolBar) }

  spacing = 6
  children = List(assignmentLabel, assignmentList, assignmentDetailsPane)

  assignmentList.selectionModel().selectedItemProperty().onChange { (_, _, selectedAssignment) => println(selectedAssignment) }
  assignmentPropsButton.onAction = { ae: ActionEvent => println(ae) }
  assignmentAddButton.onAction = { ae: ActionEvent => println(ae) }
}