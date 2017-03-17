package hs

import com.typesafe.config.ConfigFactory
import hs.domain.Repository
import hs.view._
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
  import repository._
  import MenuView._
  import StudentView._
  import GradeView._
  import CourseView._
  import AssignmentView._

  val northPane = new HBox { spacing = 6; children = List(studentPane, new Separator { orientation = Orientation.Vertical }, gradePane) }
  val southPane = new HBox { spacing = 6; children = List(coursesPane, assignmentsPane) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); children = List(menuBar, northPane, new Separator(), southPane)}
  stage = new JFXApp.PrimaryStage { scene = new Scene { root = contentPane }; title = "Homeschool"; minHeight = 516; maxHeight = 516; minWidth = 684; maxWidth = 684 }

  sys.addShutdownHook { closeRepository() }
}