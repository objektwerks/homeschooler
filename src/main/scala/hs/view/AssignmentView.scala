package hs.view

import hs.domain.Assignment

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class AssignmentView extends VBox {
  val assignmentsLabel = new Label { text = "Assignments:" }
  val assignmentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Assignment](a => a.task) )
  val assignmentsList = new ListView[Assignment] { prefWidth = 333; items = ObservableBuffer[Assignment](); cellFactory = assignmentCellFactory }
  val assignedDate = new Label { text = "00/00/0000" }
  val toLabel = new Label { text = " - " }
  val completedDate = new Label { text = "00/00/0000" }
  val scoreLabel = new Label { text = "0.0" }
  val splitLabel = new Label { text = " / " }
  val totalLabel = new Label { text = "0.0" }
  val assignmentsPropsButton = new Button { text = "*" }
  val assignmentsAddButton = new Button { text = "+" }
  val assignmentsToolBar = new HBox { spacing = 6; children = List(assignmentsPropsButton, assignmentsAddButton) }
  val assignmentsDetailsPane = new HBox { spacing = 6; children = List(assignedDate, toLabel, completedDate, scoreLabel, splitLabel, totalLabel, assignmentsToolBar) }
  spacing = 6
  children = List(assignmentsLabel, assignmentsList, assignmentsDetailsPane)
}