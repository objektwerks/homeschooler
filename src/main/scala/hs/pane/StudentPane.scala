package hs.pane

import hs.dialog.StudentDialog
import hs.entity.Student
import hs.model.Model

import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class StudentPane extends HBox {
  val studentLabel = new Label { text = "Student:" }
  val studentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Student](s => s.name) )
  val studentComboBox = new ComboBox[Student] { prefHeight = 25; prefWidth = 203; items = Model.studentList; cellFactory = studentCellFactory }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }

  spacing = 6
  children = List(studentLabel, studentComboBox, studentPropsButton, studentAddButton)

  Model.selectedStudent <== studentComboBox.selectionModel().selectedItemProperty()

  Model.selectedStudent.onChange { (_, _, selectedStudent) =>
    studentPropsButton.disable = false
    studentComboBox.selectionModel().select(selectedStudent)
  }

  studentPropsButton.onAction = { _ => update(studentComboBox.selectionModel().getSelectedIndex, studentComboBox.selectionModel().getSelectedItem) }

  studentAddButton.onAction = { _ => add(Student()) }

  def update(selectedIndex: Int, student: Student): Unit = {
    new StudentDialog(student).showAndWait() match {
      case Some(Student(id, name, born)) => Model.update(selectedIndex, Student(id, name, born))
      case _ =>
    }
  }

  def add(student: Student): Unit = {
    new StudentDialog(student).showAndWait() match {
      case Some(Student(id, name, born)) => Model.add(Student(id, name, born))
      case _ =>
    }
  }
}