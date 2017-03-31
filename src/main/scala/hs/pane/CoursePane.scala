package hs.pane

import com.typesafe.config.Config
import hs.dialog.CourseDialog
import hs.entity.Course
import hs.model.Model
import hs.view.View

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane(conf: Config, model: Model) extends VBox {
  val courseLabel = new Label { text = "Courses:" }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseListView = new ListView[Course] { minHeight = 300; items = model.courseList; cellFactory = courseCellFactory
                                              selectionModel().selectionMode = SelectionMode.Single }
  val coursePropsButton = new Button { graphic = View.editImageView(); prefHeight = 25; disable = true }
  val courseAddButton = new Button { graphic = View.addImageView(); prefHeight = 25; disable = true }
  val courseToolBar = new HBox { spacing = 6; children = List(coursePropsButton, courseAddButton) }

  spacing = 6
  children = List(courseLabel, courseListView, courseToolBar)

  model.selectedGrade.onChange { (_, _, selectedGrade) =>
    model.listCourses(selectedGrade)
    courseAddButton.disable = false
  }

  courseListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedCourse) =>
    if (selectedCourse != null) {
      model.selectedCourse.value = selectedCourse.id
      coursePropsButton.disable = false
    }
  }

  coursePropsButton.onAction = { _ => update(courseListView.selectionModel().getSelectedIndex,
                                             courseListView.selectionModel().getSelectedItem) }

  courseAddButton.onAction = { _ => add(Course(gradeid = model.selectedGrade.value)) }

  def update(selectedIndex: Int, course: Course): Unit = {
    new CourseDialog(conf, course).showAndWait() match {
      case Some(Course(id, gradeid, name)) =>
        model.updateCourse(selectedIndex, Course(id, gradeid, name))
        courseListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }

  def add(course: Course): Unit = {
    new CourseDialog(conf, course).showAndWait() match {
      case Some(Course(id, gradeid, name)) =>
        val newCourse = model.addCourse(Course(id, gradeid, name))
        courseListView.selectionModel().select(newCourse)
      case _ =>
    }
  }
}