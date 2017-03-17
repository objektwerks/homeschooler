package hs

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox

object StudentView {
  val studentLabel = new Label { text = "Student:" }
  val studentComboBox = new ComboBox[Student] { prefHeight = 25; prefWidth = 203; items = ObservableBuffer[Student]() }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25 }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }
  val studentPane = new HBox { spacing = 6; children = List(studentLabel, studentComboBox, studentPropsButton, studentAddButton) }
}