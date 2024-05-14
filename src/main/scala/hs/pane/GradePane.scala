package hs.pane

import scalafx.Includes.*
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}

import hs.{Context, Grade, Model}
import hs.dialog.GradeDialog

class GradePane(context: Context, model: Model) extends VBox:
  val gradeLabel = new Label:
    text = context.grades

  val gradeListView = new ListView[Grade]:
    minHeight = 50
    items = model.gradeList
    cellFactory = (cell, grade) => { cell.text =  grade.year }

  val gradeAddButton = new Button:
    graphic = context.addImageView
    prefHeight = 25
    disable = true

  val gradeEditButton = new Button:
    graphic = context.editImageView
    prefHeight = 25
    disable = true

  val gradeToolBar = new HBox:
    spacing = 6
    children = List(gradeAddButton, gradeEditButton)

  spacing = 6
  children = List(gradeLabel, gradeListView, gradeToolBar)

  model.selectedStudentId.onChange { (_, _, selectedStudentId) =>
    model.listGrades(selectedStudentId.intValue)
    gradeAddButton.disable = false
  }

  gradeListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedGrade) =>
    // model.update executes a remove and add on items. the remove passes a null selectedGrade!
    if (selectedGrade != null) then
      model.selectedGradeId.value = selectedGrade.id
      gradeEditButton.disable = false
  }

  gradeListView.onMouseClicked = { event =>
    if (event.getClickCount == 2 &&
        gradeListView.selectionModel().getSelectedItem != null) then update()
  }

  gradeAddButton.onAction = { _ => add( Grade(studentid = model.selectedStudentId.value) ) }

  gradeEditButton.onAction = { _ => update() }

  def add(grade: Grade): Unit =
    GradeDialog(context, grade).showAndWait() match
      case Some(Grade(id, studentid, year, started, completed)) =>
        val newGrade = model.addGrade(
          Grade(id, studentid, year, started, completed)
        )
        gradeListView.selectionModel().select(newGrade)
      case _ =>

  def update(): Unit =
    val selectedIndex = gradeListView.selectionModel().getSelectedIndex
    val grade = gradeListView.selectionModel().getSelectedItem
    GradeDialog(context, grade).showAndWait() match
      case Some(Grade(id, studentid, year, started, completed)) =>
        model.updateGrade(
          selectedIndex,
          Grade(id, studentid, year, started, completed)
        )
        gradeListView.selectionModel().select(selectedIndex)
      case _ =>