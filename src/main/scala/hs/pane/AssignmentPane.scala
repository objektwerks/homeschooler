package hs.pane

import com.typesafe.config.Config

import hs.Images
import hs.dialog.{AssignmentChartDialog, AssignmentDialog}
import hs.Assignment
import hs.Model

import scalafx.Includes._
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

class AssignmentPane(conf: Config, model: Model) extends VBox {
  val assignmentLabel = new Label {
    text = conf.getString("assignments")
  }
  val assignmentListView = new ListView[Assignment] {
    minHeight = 300;
    items = model.assignmentList;
    cellFactory = (cell, assignment) => { cell.text =  assignment.task }
    selectionModel().selectionMode = SelectionMode.Single
  }
  val assignmentAddButton = new Button {
    graphic = Images.addImageView; prefHeight = 25; disable = true
  }
  val assignmentEditButton = new Button {
    graphic = Images.editImageView; prefHeight = 25; disable = true
  }
  val assignmentChartButton = new Button {
    graphic = Images.lineChartImageView; prefHeight = 25; disable = true
  }
  val assignmentToolBar = new HBox {
    spacing = 6; children = List(assignmentAddButton, assignmentEditButton, assignmentChartButton)
  }

  spacing = 6
  children = List(assignmentLabel, assignmentListView, assignmentToolBar)

  model.selectedCourseId.onChange { (_, _, selectedCourseId) =>
    model.listAssignments(selectedCourseId.intValue)
    assignmentAddButton.disable = false
    assignmentChartButton.disable = if (model.assignmentList.nonEmpty) false else true
  }

  assignmentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedAssignment) =>
    // model.update executes a remove and add on items. the remove passes a null selectedAssignment!
    if (selectedAssignment != null) {
      model.selectedAssignmentId.value = selectedAssignment.id
      assignmentEditButton.disable = false
      assignmentChartButton.disable = false
    }
  }

  assignmentListView.onMouseClicked = { event =>
    if (event.getClickCount == 2 && assignmentListView.selectionModel().getSelectedItem != null) update()
  }

  assignmentAddButton.onAction = { _ => add(Assignment(courseid = model.selectedCourseId.value)) }

  assignmentEditButton.onAction = { _ => update() }

  assignmentChartButton.onAction = { _ =>
    new AssignmentChartDialog(conf, model.assignmentList).showAndWait()
    ()
  }

  def add(assignment: Assignment): Unit = {
    new AssignmentDialog(conf, assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        val newAssignment = model.addAssignment(Assignment(id, courseid, task, assigned, completed, score))
        assignmentListView.selectionModel().select(newAssignment)
      case _ =>
    }
  }

  def update(): Unit = {
    val selectedIndex = assignmentListView.selectionModel().getSelectedIndex
    val assignment = assignmentListView.selectionModel().getSelectedItem
    new AssignmentDialog(conf, assignment).showAndWait() match {
      case Some(Assignment(id, courseid, task, assigned, completed, score)) =>
        model.updateAssignment(selectedIndex, Assignment(id, courseid, task, assigned, completed, score))
        assignmentListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }
}