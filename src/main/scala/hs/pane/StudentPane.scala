package hs.pane

import hs.{Model, Store}
import hs.dialog.StudentDialog
import hs.repository.Student

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

class StudentPane extends HBox {
  val studentLabel = new Label { text = "Student:" }
  val studentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Student](s => s.name) )
  val studentComboBox = new ComboBox[Student] { prefHeight = 25; prefWidth = 203; items = Model.students; cellFactory = studentCellFactory }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }

  spacing = 6
  children = List(studentLabel, studentComboBox, studentPropsButton, studentAddButton)

  Model.selectedStudent <== studentComboBox.value
  studentComboBox.selectionModel().selectedItemProperty().onChange { (_, _, _) => studentPropsButton.disable = false }
  studentPropsButton.onAction = { _ => save(studentComboBox.value.value) }
  studentAddButton.onAction = { _ => save(Student()) }

  def save(student: Student): Unit = {
    val result = new StudentDialog(student).showAndWait()
    result match {
      case Some(Student(id, name, born)) =>
        import Store.repository._
        val student = Student(id, name, born)
        val studentId = await(students.save(student))
        val persistedStudent = student.copy(id = studentId.get)
        if (id == 0) {
          Model.students += persistedStudent
          studentComboBox.value = persistedStudent
        }
      case _ => println("Student dialog failed!")
    }
  }
}