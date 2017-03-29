package hs.pane

import com.typesafe.config.Config
import hs.dialog.StudentDialog
import hs.entity.Student
import hs.model.Model

import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class StudentPane(conf: Config, model: Model) extends HBox {
  val studentLabel = new Label { text = "Student:" }
  val studentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Student](s => s.name) )
  val studentComboBox = new ComboBox[Student] { prefHeight = 25; prefWidth = 203; items = model.studentList; cellFactory = studentCellFactory }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }

  spacing = 6
  children = List(studentLabel, studentComboBox, studentPropsButton, studentAddButton)

  model.selectedStudent <== studentComboBox.selectionModel().selectedItemProperty()

  model.studentList.onChange { studentPropsButton.disable = model.studentList.isEmpty }

  studentPropsButton.onAction = { _ => update(studentComboBox.selectionModel().getSelectedIndex, studentComboBox.selectionModel().getSelectedItem) }

  studentAddButton.onAction = { _ => add(Student()) }

  def update(selectedIndex: Int, student: Student): Unit = {
    new StudentDialog(student).showAndWait() match {
      case Some(Student(id, name, born)) => model.updateStudent(selectedIndex, Student(id, name, born))
      case _ =>
    }
  }

  def add(student: Student): Unit = {
    new StudentDialog(student).showAndWait() match {
      case Some(Student(id, name, born)) => model.addStudent(Student(id, name, born))
      case _ =>
    }
  }
}