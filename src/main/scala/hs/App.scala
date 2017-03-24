package hs

import com.typesafe.config.ConfigFactory
import hs.pane._
import hs.repository.Repository
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

import scalafx.application.JFXApp
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

object App extends JFXApp {
  val config = DatabaseConfig.forConfig[JdbcProfile]("app", ConfigFactory.load("app.conf"))
  val repository = new Repository(config = config, profile = H2Profile)

  val northPane = new HBox { spacing = 6; children = List(new StudentPane(), new Separator { orientation = Orientation.Vertical }, new GradePane()) }
  val southPane = new HBox { spacing = 6; children = List(new CoursePane(), new AssignmentPane()) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); children = List(new MenuPane(), northPane, new Separator(), southPane)}
  val sceneGraph = new Scene { root = contentPane }
  stage = new JFXApp.PrimaryStage { scene = sceneGraph; title = "Homeschool"; minHeight = 516; maxHeight = 516; minWidth = 684; maxWidth = 684 }

  sys.addShutdownHook { repository.close() }
}