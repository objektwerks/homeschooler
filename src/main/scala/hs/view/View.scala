package hs.view

import com.typesafe.config.Config
import hs.model.Model
import hs.pane._

import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control.Separator
import scalafx.scene.layout.{HBox, VBox}

class View(conf: Config, model: Model) {
  val menuPane = new MenuPane()
  val studentPane = new StudentPane(conf, model)
  val gradePane = new GradePane(conf, model)
  val coursePane = new CoursePane(conf, model)
  val assignmentPane = new AssignmentPane(conf, model)

  val northPane = new HBox { spacing = 6; children = List(studentPane, new Separator { orientation = Orientation.Vertical }, gradePane) }
  val southPane = new HBox { spacing = 6; children = List(coursePane, assignmentPane) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); children = List(menuPane, northPane, new Separator(), southPane) }
  val sceneGraph = new Scene { root = contentPane }
}