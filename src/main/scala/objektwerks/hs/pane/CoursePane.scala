package objektwerks.hs.pane

import com.typesafe.config.Config
import objektwerks.hs.dialog.CourseDialog
import objektwerks.hs.entity.Course
import objektwerks.hs.image.Images
import objektwerks.hs.model.Model

import scalafx.Includes._
import scalafx.scene.control.cell.TextFieldListCell
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.util.StringConverter

class CoursePane(conf: Config, model: Model) extends VBox {
  val courseLabel = new Label { text = conf.getString("courses") }
  val courseCellFactory = TextFieldListCell.forListView( StringConverter.toStringConverter[Course](c => c.name) )
  val courseListView = new ListView[Course] { minHeight = 300; items = model.courseList; cellFactory = courseCellFactory
                                              selectionModel().selectionMode = SelectionMode.Single }
  val courseAddButton = new Button { graphic = Images.addImageView(); prefHeight = 25; disable = true }
  val courseEditButton = new Button { graphic = Images.editImageView(); prefHeight = 25; disable = true }
  val courseToolBar = new HBox { spacing = 6; children = List(courseAddButton, courseEditButton) }

  spacing = 6
  children = List(courseLabel, courseListView, courseToolBar)

  model.selectedGradeId.onChange { (_, _, selectedGrade) =>
    model.listCourses(selectedGrade)
    courseAddButton.disable = false
  }

  courseListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedCourse) =>
    // model.update executes a remove and add on items. the remove passes a null selectedCourse!
    if (selectedCourse != null) {
      model.selectedCourseId.value = selectedCourse.id
      courseEditButton.disable = false
    }
  }

  courseAddButton.onAction = { _ => add(Course(gradeid = model.selectedGradeId.value)) }

  courseEditButton.onAction = { _ => update(courseListView.selectionModel().getSelectedIndex,
                                             courseListView.selectionModel().getSelectedItem) }

  def add(course: Course): Unit = {
    new CourseDialog(conf, course).showAndWait() match {
      case Some(Course(id, gradeid, name, started, completed)) =>
        val newCourse = model.addCourse(Course(id, gradeid, name, started, completed))
        courseListView.selectionModel().select(newCourse)
      case _ =>
    }
  }

  def update(selectedIndex: Int, course: Course): Unit = {
    new CourseDialog(conf, course).showAndWait() match {
      case Some(Course(id, gradeid, name, started, completed)) =>
        model.updateCourse(selectedIndex, Course(id, gradeid, name, started, completed))
        courseListView.selectionModel().select(selectedIndex)
      case _ =>
    }
  }
}