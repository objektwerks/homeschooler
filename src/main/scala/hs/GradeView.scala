package hs

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, ComboBox, Label}
import scalafx.scene.layout.HBox

object GradeView {
  val gradeLabel = new Label { text = "Grade:" }
  val gradeComboBox = new ComboBox[Grade] { prefHeight = 25; prefWidth = 60; items = ObservableBuffer[Grade]() }
  val gradePropsButton = new Button { text = "*"; prefHeight = 25 }
  val gradeAddButton = new Button { text = "+"; prefHeight = 25 }
  val gradePane = new HBox { spacing = 6; children = List(gradeLabel, gradeComboBox, gradePropsButton, gradeAddButton) }
}