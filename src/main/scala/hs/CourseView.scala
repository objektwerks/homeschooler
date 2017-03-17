package hs

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, Label, ListView}
import scalafx.scene.layout.{HBox, VBox}

object CourseView {
  val coursesLabel = new Label { text = "Courses:" }
  val coursesList = new ListView[Course] { prefWidth = 333; items = ObservableBuffer[Course]() }
  val coursesPropsButton = new Button { text = "*" }
  val coursesAddButton = new Button { text = "+" }
  val coursesToolBar = new HBox { spacing = 6; children = List(coursesPropsButton, coursesAddButton)}
  val coursesPane = new VBox { spacing = 6; children = List(coursesLabel, coursesList, coursesToolBar) }
}