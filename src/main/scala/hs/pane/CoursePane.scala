package hs.pane

import hs.dialog.CourseDialog
import hs.repository.Course
import hs.{Model, Store}

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane() extends VBox {
  val courseLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseList = new ListView[Course] { prefWidth = 333; items = Model.courseList; cellFactory = courseCellFactory; selectionModel().selectionMode = SelectionMode.Single }
  val coursePropsButton = new Button { text = "*"; prefHeight = 25; disable = true }
  val courseAddButton = new Button { text = "+"; prefHeight = 25; disable = true }
  val courseToolBar = new HBox { spacing = 6; children = List(coursePropsButton, courseAddButton) }

  spacing = 6
  children = List(courseLabel, courseList, courseToolBar)


  Model.selectedCourse <== courseList.selectionModel().selectedItemProperty()
  Model.selectedCourse.onChange {
    coursePropsButton.disable = false
    courseAddButton.disable = false
  }
  coursePropsButton.onAction = { _ => save(courseList.selectionModel().getSelectedItem) }
  courseAddButton.onAction = { _ => save(Course(gradeid = Model.selectedGrade.value.id)) }

  def save(course: Course): Unit = {
    val result = new CourseDialog(course).showAndWait()
    result match {
      case Some(Course(id, gradeid, name)) =>
        import Store.repository._
        val course = Course(id, gradeid, name)
        val courseId = await(courses.save(course))
        val persistedCourse = course.copy(id = courseId.get)
        if (id == 0) {
          Model.courseList += persistedCourse
          courseList.selectionModel().select(persistedCourse)
        }
      case _ => println("Course dialog failed!")
    }
  }
}