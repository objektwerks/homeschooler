package hs.pane

import hs.repository.Course

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane extends VBox {
  val courseLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseList = new ListView[Course] { prefWidth = 333; items = ObservableBuffer[Course](); cellFactory = courseCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val coursePropsButton = new Button { text = "*"; prefHeight = 25 }
  val courseAddButton = new Button { text = "+"; prefHeight = 25 }
  val courseToolBar = new HBox { spacing = 6; children = List(coursePropsButton, courseAddButton) }

  spacing = 6
  children = List(courseLabel, courseList, courseToolBar)

  courseList.selectionModel().selectedItemProperty().onChange { (_, _, selectedCourse) => println(selectedCourse) }
  coursePropsButton.onAction = { ae: ActionEvent => println(ae) }
  courseAddButton.onAction = { ae: ActionEvent => println(ae) }
}