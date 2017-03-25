package hs

import com.typesafe.config.ConfigFactory
import hs.pane._

import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

object App extends JFXApp {
  val conf = ConfigFactory.load("app.conf")
  val menuPane = new MenuPane()
  val studentPane = new StudentPane()
  val gradePane = new GradePane(studentPane)
  val coursePane = new CoursePane(gradePane)
  val assignmentPane = new AssignmentPane(coursePane)

  val northPane = new HBox { spacing = 6; children = List(studentPane, new Separator { orientation = Orientation.Vertical }, gradePane) }
  val southPane = new HBox { spacing = 6; children = List(coursePane, assignmentPane) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); children = List(menuPane, northPane, new Separator(), southPane) }
  val sceneGraph = new Scene { root = contentPane }
  stage = new JFXApp.PrimaryStage { scene = sceneGraph; title = conf.getString("title"); minHeight = 516; maxHeight = 516; minWidth = 684; maxWidth = 684 }

  sys.addShutdownHook { Store.repository.close() }
}