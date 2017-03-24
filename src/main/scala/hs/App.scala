package hs

import hs.pane._

import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

object App extends JFXApp {
  val northPane = new HBox { spacing = 6; children = List(new StudentPane(), new Separator { orientation = Orientation.Vertical }, new GradePane()) }
  val southPane = new HBox { spacing = 6; children = List(new CoursePane(), new AssignmentPane()) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); children = List(new MenuPane(), northPane, new Separator(), southPane) }
  val sceneGraph = new Scene { root = contentPane }
  stage = new JFXApp.PrimaryStage { scene = sceneGraph; title = "Homeschool"; minHeight = 516; maxHeight = 516; minWidth = 684; maxWidth = 684 }

  sys.addShutdownHook { Store.repository.close() }
}