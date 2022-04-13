package objektwerks.hs.pane

import com.typesafe.config.Config

import objektwerks.hs.Resources
import objektwerks.hs.dialog.StudentDialog
import objektwerks.hs.entity.Student
import objektwerks.hs.model.Model

import scalafx.Includes._
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}

class StudentPane(conf: Config, model: Model) extends VBox {
  val studentLabel = new Label {
    text = conf.getString("students")
  }
  val studentListView = new ListView[Student] {
    minHeight = 50
    items = model.studentList
    cellFactory = (cell, student) => { cell.text =  student.name }
  }
  val studentAddButton = new Button {
    graphic = Resources.addImageView; prefHeight = 25
  }
  val studentEditButton = new Button {
    graphic = Resources.editImageView; prefHeight = 25; disable = true
  }
  val studentToolBar = new HBox {
    spacing = 6; children = List(studentAddButton, studentEditButton)
  }

  spacing = 6
  children = List(studentLabel, studentListView, studentToolBar)

  studentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedStudent) =>
    // model.update executes a remove and add on items. the remove passes a null selectedStudent!
    if (selectedStudent != null) {
      model.selectedStudentId.value = selectedStudent.id
      studentEditButton.disable = false
    }
  }

  studentListView.onMouseClicked = { event =>
    if (event.getClickCount == 2 && studentListView.selectionModel().getSelectedItem != null) update()
  }

  studentAddButton.onAction = { _ => add(Student()) }

  studentEditButton.onAction = { _ => update() }

  def add(student: Student): Unit = {
    new StudentDialog(conf, student).showAndWait() match {
      case Some(Student(id, name, born)) =>
        val newStudent = model.addStudent(Student(id, name, born))
        studentListView.selectionModel().select(newStudent)
      case _ =>
    }
  }

  def update(): Unit = {
    val selectedIndex = studentListView.selectionModel().getSelectedIndex
    val student = studentListView.selectionModel().getSelectedItem
    new StudentDialog(conf, student).showAndWait() match {
      case Some(Student(id, name, born)) =>
        model.updateStudent(selectedIndex, Student(id, name, born))
        studentListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }
}