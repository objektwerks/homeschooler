package hs.pane

import com.typesafe.config.Config

import scalafx.Includes.*
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}

import hs.{Grade, Images, Model}
import hs.dialog.GradeDialog

class GradePane(conf: Config, model: Model) extends VBox:
  val gradeLabel = new Label:
    text = conf.getString("grades")

  val gradeListView = new ListView[Grade]:
    minHeight = 50
    items = model.gradeList
    cellFactory = (cell, grade) => { cell.text =  grade.year }

  val gradeAddButton = new Button:
    graphic = Images.addImageView; prefHeight = 25; disable = true

  val gradeEditButton = new Button:
    graphic = Images.editImageView; prefHeight = 25; disable = true

  val gradeToolBar = new HBox:
    spacing = 6; children = List(gradeAddButton, gradeEditButton)

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
    if (event.getClickCount == 2 && gradeListView.selectionModel().getSelectedItem != null) update()
  }

  gradeAddButton.onAction = { _ => add(Grade(studentid = model.selectedStudentId.value)) }

  gradeEditButton.onAction = { _ => update() }

  def add(grade: Grade): Unit =
    GradeDialog(conf, grade).showAndWait() match
      case Some(Grade(id, studentid, year, started, completed)) =>
        val newGrade = model.addGrade(Grade(id, studentid, year, started, completed))
        gradeListView.selectionModel().select(newGrade)
      case _ =>

  def update(): Unit =
    val selectedIndex = gradeListView.selectionModel().getSelectedIndex
    val grade = gradeListView.selectionModel().getSelectedItem
    GradeDialog(conf, grade).showAndWait() match
      case Some(Grade(id, studentid, year, started, completed)) =>
        model.updateGrade(selectedIndex, Grade(id, studentid, year, started, completed))
        gradeListView.selectionModel().select(selectedIndex)
      case _ =>