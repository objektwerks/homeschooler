package hs.pane

import com.typesafe.config.Config

import scalafx.Includes.*
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}

import hs.{Images, Model, Student}
import hs.dialog.StudentDialog

class StudentPane(conf: Config, model: Model) extends VBox:
  val studentLabel = new Label:
    text = conf.getString("students")

  val studentListView = new ListView[Student]:
    minHeight = 50
    items = model.studentList
    cellFactory = (cell, student) => { cell.text =  student.name }

  val studentAddButton = new Button:
    graphic = Images.addImageView
    prefHeight = 25

  val studentEditButton = new Button:
    graphic = Images.editImageView
    prefHeight = 25
    disable = true

  val studentToolBar = new HBox:
    spacing = 6
    children = List(studentAddButton, studentEditButton)

  spacing = 6
  children = List(studentLabel, studentListView, studentToolBar)

  studentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedStudent) =>
    // model.update executes a remove and add on items. the remove passes a null selectedStudent!
    if (selectedStudent != null) then
      model.selectedStudentId.value = selectedStudent.id
      studentEditButton.disable = false
  }

  studentListView.onMouseClicked = { event =>
    if (event.getClickCount == 2 &&
        studentListView.selectionModel().getSelectedItem != null) then update()
  }

  studentAddButton.onAction = { _ => add( Student() ) }

  studentEditButton.onAction = { _ => update() }

  def add(student: Student): Unit =
    StudentDialog(conf, student).showAndWait() match
      case Some(Student(id, name, born)) =>
        val newStudent = model.addStudent(Student(id, name, born))
        studentListView.selectionModel().select(newStudent)
      case _ =>

  def update(): Unit =
    val selectedIndex = studentListView.selectionModel().getSelectedIndex
    val student = studentListView.selectionModel().getSelectedItem
    StudentDialog(conf, student).showAndWait() match
      case Some(Student(id, name, born)) =>
        model.updateStudent(selectedIndex, Student(id, name, born))
        studentListView.selectionModel().select(selectedIndex)
      case _ =>