package hs

import com.typesafe.config.ConfigFactory
import hs.domain.Repository
import hs.pane._
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
  val menuPane = new MenuPane()
  val studentPane = new StudentPane()
  val gradePane = new GradePane()
  val coursePane = new CoursePane()
  val assignmentPane = new AssignmentPane()

  val northPane = new HBox { spacing = 6; children = List(studentPane, new Separator { orientation = Orientation.Vertical }, gradePane) }
  val southPane = new HBox { spacing = 6; children = List(coursePane, assignmentPane) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); children = List(menuPane, northPane, new Separator(), southPane)}
  val sceneGraph = new Scene { root = contentPane }
  stage = new JFXApp.PrimaryStage { scene = sceneGraph; title = "Homeschool"; minHeight = 516; maxHeight = 516; minWidth = 684; maxWidth = 684 }

  sys.addShutdownHook { repository.close() }
}