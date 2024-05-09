package hs.pane

import com.typesafe.config.Config

import scalafx.Includes.*
import scalafx.scene.control.{Button, Label, ListView, SelectionMode}
import scalafx.scene.layout.{HBox, VBox}

import hs.{Course, Images, Model}
import hs.dialog.{CourseChartDialog, CourseDialog}

class CoursePane(conf: Config, model: Model) extends VBox:
  val courseLabel = new Label:
    text = conf.getString("courses")

  val courseListView = new ListView[Course]:
    minHeight = 300
    items = model.courseList
    cellFactory = (cell, course) => { cell.text =  course.name }
    selectionModel().selectionMode = SelectionMode.Single

  val courseAddButton = new Button:
    graphic = Images.addImageView
    prefHeight = 25
    disable = true

  val courseEditButton = new Button:
    graphic = Images.editImageView
    prefHeight = 25
    disable = true

  val courseChartButton = new Button:
    graphic = Images.barChartImageView
    prefHeight = 25
    disable = true

  val courseToolBar = new HBox:
    spacing = 6
    children = List(courseAddButton, courseEditButton, courseChartButton)

  spacing = 6
  children = List(courseLabel, courseListView, courseToolBar)

  model.selectedGradeId.onChange { (_, _, selectedGradeId) =>
    model.listCourses(selectedGradeId.intValue)
    courseAddButton.disable = false
    courseChartButton.disable = if (model.courseList.nonEmpty) then false else true
  }

  courseListView.selectionModel().selectedItemProperty().onChange { (_, _, selectedCourse) =>
    // model.update executes a remove and add on items. the remove passes a null selectedCourse!
    if (selectedCourse != null) then
      model.selectedCourseId.value = selectedCourse.id
      courseEditButton.disable = false
      courseChartButton.disable = false
  }

  courseListView.onMouseClicked = { event =>
    if (event.getClickCount == 2 &&
        courseListView.selectionModel().getSelectedItem != null) then update()
  }

  courseAddButton.onAction = { _ => add(Course(gradeid = model.selectedGradeId.value)) }

  courseEditButton.onAction = { _ => update() }

  courseChartButton.onAction = { _ => CourseChartDialog(conf, model.courseList, model).showAndWait() }

  def add(course: Course): Unit =
    CourseDialog(conf, course).showAndWait() match
      case Some(Course(id, gradeid, name, started, completed)) =>
        val newCourse = model.addCourse(
          Course(id, gradeid, name, started, completed)
        )
        courseListView.selectionModel().select(newCourse)
      case _ =>

  def update(): Unit =
    val selectedIndex = courseListView.selectionModel().getSelectedIndex
    val course = courseListView.selectionModel().getSelectedItem
    CourseDialog(conf, course).showAndWait() match
      case Some(Course(id, gradeid, name, started, completed)) =>
        model.updateCourse(
          selectedIndex,
          Course(id, gradeid, name, started, completed)
        )
        courseListView.selectionModel().select(selectedIndex)
      case _ =>