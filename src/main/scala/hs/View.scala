package hs

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.SplitPane
import scalafx.scene.layout.{Priority, VBox}

import hs.pane.*

class View(context: Context, model: Model):
  val studentPane = new StudentPane(context, model)
  val gradePane = new GradePane(context, model)
  val coursePane = new CoursePane(context, model)
  val assignmentPane = new AssignmentPane(context, model)

  val menuPane = new MenuPane(context)

  val westPane = new VBox:
    spacing = 6
    padding = Insets(6)
    children = List(studentPane, coursePane)

  val eastPane = new VBox:
    spacing = 6
    padding = Insets(6)
    children = List(gradePane, assignmentPane)

  val splitPane = new SplitPane:
    vgrow = Priority.Always
    hgrow = Priority.Always
    padding = Insets(6)
    items.addAll(westPane, eastPane)

  val contentPane = new VBox:
    prefHeight = 600
    prefWidth = 800
    spacing = 6
    padding = Insets(6)
    children = List(menuPane, splitPane)
  
  val scene = new Scene:
    root = contentPane