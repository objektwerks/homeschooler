package hs.pane

import scalafx.Includes.*
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}

import hs.{Assignment, Context, Model}
import hs.dialog.{AssignmentChartDialog, AssignmentDialog}

class AssignmentPane(context: Context, model: Model) extends VBox:
  val assignmentLabel = new Label:
    text = conf.getString("assignments")

  val assignmentListView = new ListView[Assignment]:
    minHeight = 300
    items = model.assignmentList
    cellFactory = (cell, assignment) => { cell.text =  assignment.task }
    selectionModel().selectionMode = SelectionMode.Single

  val assignmentAddButton = new Button:
    graphic = context.addImageView
    prefHeight = 25
    disable = true

  val assignmentEditButton = new Button:
    graphic = context.editImageView
    prefHeight = 25
    disable = true

  val assignmentChartButton = new Button:
    graphic = context.lineChartImageView
    prefHeight = 25
    disable = true

  val assignmentToolBar = new HBox:
    spacing = 6; children = List(assignmentAddButton, assignmentEditButton, assignmentChartButton)

  spacing = 6
  children = List(assignmentLabel, assignmentListView, assignmentToolBar)

  model.selectedCourseId.onChange { (_, _, selectedCourseId) =>
    model.listAssignments(selectedCourseId.intValue)
    assignmentAddButton.disable = false
    assignmentChartButton.disable = if (model.assignmentList.nonEmpty) then false else true
  }

  assignmentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedAssignment) =>
    // model.update executes a remove and add on items. the remove passes a null selectedAssignment!
    if (selectedAssignment != null) then
      model.selectedAssignmentId.value = selectedAssignment.id
      assignmentEditButton.disable = false
      assignmentChartButton.disable = false
  }

  assignmentListView.onMouseClicked = { event =>
    if (event.getClickCount == 2 &&
    assignmentListView.selectionModel().getSelectedItem != null) then update()
  }

  assignmentAddButton.onAction = { _ => add(Assignment(courseid = model.selectedCourseId.value)) }

  assignmentEditButton.onAction = { _ => update() }

  assignmentChartButton.onAction = { _ => AssignmentChartDialog(context, model.assignmentList).showAndWait() }

  def add(assignment: Assignment): Unit =
    AssignmentDialog(context, assignment).showAndWait() match
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        val newAssignment = model.addAssignment(
          Assignment(id, courseid, task, assigned, completed, score)
        )
        assignmentListView.selectionModel().select(newAssignment)
      case _ =>

  def update(): Unit =
    val selectedIndex = assignmentListView.selectionModel().getSelectedIndex
    val assignment = assignmentListView.selectionModel().getSelectedItem
    AssignmentDialog(context, assignment).showAndWait() match
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        model.updateAssignment(
          selectedIndex,
          Assignment(id, courseid, task, assigned, completed, score)
        )
        assignmentListView.selectionModel().select(selectedIndex)
      case _ =>