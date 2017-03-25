package hs.pane

import hs.Store
import hs.dialog.CourseDialog
import hs.repository.Course

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane() extends VBox {
  val courseLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseList = new ListView[Course] { prefWidth = 333; items = ObservableBuffer[Course](); cellFactory = courseCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val coursePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val courseAddButton = new Button { text = "+"; prefHeight = 25; disable = true }
  val courseToolBar = new HBox { spacing = 6; children = List(coursePropsButton, courseAddButton) }

  spacing = 6
  children = List(courseLabel, courseList, courseToolBar)

  courseList.selectionModel().selectedItemProperty().onChange { (_, _, selectedCourse) =>
    coursePropsButton.disable = false
    courseAddButton.disable = false
    println(selectedCourse)
  }
  coursePropsButton.onAction = { _ => handleAction(courseList.selectionModel().getSelectedItem) }
  courseAddButton.onAction = { _ => handleAction(Course(gradeid = 1)) }

  def handleAction(course: Course): Unit = {
    import Store.repository._
    val result = new CourseDialog(course).showAndWait()
    result match {
      case Some(Course(id, gradeid, name)) => await(courses.save(Course(id, gradeid, name)))
      case _ => println("Course dialog failed!")
    }
  }
}