package objektwerks.hs.pane

import com.typesafe.config.Config
import objektwerks.hs.dialog.StudentDialog
import objektwerks.hs.entity.Student
import objektwerks.hs.image.Images
import objektwerks.hs.model.Model

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class StudentPane(conf: Config, model: Model) extends VBox {
  val studentLabel = new Label { text = conf.getString("students") }
  val studentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Student](s => s.name) )
  val studentListView = new ListView[Student] { minHeight = 50; items = model.studentList; cellFactory = studentCellFactory }
  val studentPropsButton = new Button { graphic = Images.editImageView(); prefHeight = 25; disable = true }
  val studentAddButton = new Button { graphic = Images.addImageView(); prefHeight = 25 }
  val studentToolBar = new HBox { spacing = 6; children = List(studentPropsButton, studentAddButton) }

  spacing = 6
  children = List(studentLabel, studentListView, studentToolBar)

  studentListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedStudent) =>
    if (selectedStudent != null) { // model.update yields a remove and add to items. the remove passes a null selectedStudent!
      model.selectedStudentId.value = selectedStudent.id
      studentPropsButton.disable = false
    }
  }

  studentPropsButton.onAction = { _ => update(studentListView.selectionModel().getSelectedIndex,
                                              studentListView.selectionModel().getSelectedItem) }

  studentAddButton.onAction = { _ => add(Student()) }

  def update(selectedIndex: Int, student: Student): Unit = {
    new StudentDialog(conf, student).showAndWait() match {
      case Some(Student(id, name, born)) =>
        model.updateStudent(selectedIndex, Student(id, name, born))
        studentListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }

  def add(student: Student): Unit = {
    new StudentDialog(conf, student).showAndWait() match {
      case Some(Student(id, name, born)) =>
        val newStudent = model.addStudent(Student(id, name, born))
        studentListView.selectionModel().select(newStudent)
      case _ =>
    }
  }
}