package hs.pane

import hs.repository.Course

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane extends VBox {
  val coursesLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val coursesList = new ListView[Course] { prefWidth = 333; items = ObservableBuffer[Course](); cellFactory = courseCellFactory }
  val coursesPropsButton = new Button { text = "*" }
  val coursesAddButton = new Button { text = "+" }
  val coursesToolBar = new HBox { spacing = 6; children = List(coursesPropsButton, coursesAddButton)}
  spacing = 6
  children = List(coursesLabel, coursesList, coursesToolBar)
}