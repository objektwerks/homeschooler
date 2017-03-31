package hs.pane

import javafx.{concurrent => jfxc}

import com.typesafe.config.Config
import hs.dialog.StudentDialog
import hs.entity.Student
import hs.model.Model

import scala.concurrent.ExecutionContext
import scalafx.Includes._
import scalafx.concurrent.Task
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class StudentPane(conf: Config, model: Model) extends HBox {
  val ec = ExecutionContext.global
  val studentLabel = new Label { text = "Student:" }
  val studentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Student](s => s.name) )
  val studentListView = new ListView[Student] { prefHeight = 100; prefWidth = 203; items = model.studentList; cellFactory = studentCellFactory }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }

  spacing = 6
  children = List(studentLabel, studentListView, studentPropsButton, studentAddButton)

  model.selectedStudent <== studentListView.selectionModel().selectedItemProperty()

  studentListView.selectionModel().selectedItemProperty().onChange { studentPropsButton.disable = false }

  studentPropsButton.onAction = { _ => update(studentListView.selectionModel().getSelectedIndex,
                                              studentListView.selectionModel().getSelectedItem) }

  studentAddButton.onAction = { _ => add(Student()) }

  def update(selectedIndex: Int, student: Student): Unit = {
    new StudentDialog(conf, student).showAndWait() match {
      case Some(Student(id, name, born)) =>
        ec.execute(Task(new jfxc.Task[Unit] {
          def call(): Unit  = { model.updateStudent(selectedIndex, Student(id, name, born)) }
        }))
      case _ =>
    }
  }

  def add(student: Student): Unit = {
    new StudentDialog(conf, student).showAndWait() match {
      case Some(Student(id, name, born)) =>
        ec.execute(Task(new jfxc.Task[Unit] {
          def call(): Unit  = { model.addStudent(Student(id, name, born)) }
        }))
      case _ =>
    }
  }
}