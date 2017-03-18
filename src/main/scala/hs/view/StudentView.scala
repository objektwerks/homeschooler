package hs.view

import hs.domain.Student

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox
import scalafx.util.StringConverter

object StudentView {
  val studentLabel = new Label { text = "Student:" }
  val studentCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Student](s => s.name ) )
  val studentComboBox = new ComboBox[Student] { prefHeight = 25; prefWidth = 203; items = ObservableBuffer[Student](); cellFactory = studentCellFactory }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25 }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }
  val studentPane = new HBox { spacing = 6; children = List(studentLabel, studentComboBox, studentPropsButton, studentAddButton) }
}