package hs

import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

import scalafx.application.JFXApp
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout.{HBox, VBox}

object App extends JFXApp {
  val config = DatabaseConfig.forConfig[JdbcProfile]("app", ConfigFactory.load("app.conf"))
  val repository = new Repository(config = config, profile = H2Profile)
  import repository._

  val studentLabel = new Label { text = "Student:" }
  val studentComboBox = new ComboBox[Student] { prefHeight = 25; prefWidth = 200; items = ObservableBuffer[Student]() }
  val studentPropsButton = new Button { text = "*"; prefHeight = 25 }
  val studentAddButton = new Button { text = "+"; prefHeight = 25 }
  val studentPane = new HBox { spacing = 6; children = List(studentLabel, studentComboBox, studentPropsButton, studentAddButton) }

  val gradeLabel = new Label { text = "Grade:" }
  val gradeComboBox = new ComboBox[Grade] { prefHeight = 25; prefWidth = 90; items = ObservableBuffer[Grade]() }
  val gradePropsButton = new Button { text = "*"; prefHeight = 25 }
  val gradeAddButton = new Button { text = "+"; prefHeight = 25 }
  val gradePane = new HBox { spacing = 6; children = List(gradeLabel, gradeComboBox, gradePropsButton, gradeAddButton) }

  val studentGradePane = new HBox { spacing = 6; children = List(studentPane, gradePane) }

  val coursesLabel = new Label { text = "Courses:" }
  val coursesList = new ListView[Course] { prefWidth = 300; items = ObservableBuffer[Course]() }
  val coursesPropsButton = new Button { text = "*" }
  val coursesAddButton = new Button { text = "+" }
  val coursesToolBar = new HBox { spacing = 6; children = List(coursesPropsButton, coursesAddButton)}
  val coursesPane = new VBox { spacing = 6; children = List(coursesLabel, coursesList, coursesToolBar) }

  val assignmentsLabel = new Label { text = "Assignments:" }
  val assignmentsList = new ListView[Assignment] { prefWidth = 300; items = ObservableBuffer[Assignment]() }
  val assignedDate = new Label()
  val toLabel = new Label { text = " - " }
  val completedDate = new Label()
  val scoreLabel = new Label { text = "0.0" }
  val splitLabel = new Label { text = " / " }
  val totalLabel = new Label { text = "0.0" }
  val assignmentsPropsButton = new Button { text = "*" }
  val assignmentsAddButton = new Button { text = "+" }
  val assignmentsToolBar = new HBox { spacing = 6; children = List(assignmentsPropsButton, assignmentsAddButton) }
  val assignmentsDetailsPane = new HBox { spacing = 6; children = List(assignedDate, toLabel, completedDate, scoreLabel, splitLabel, totalLabel, assignmentsToolBar) }
  val assignmentsPane = new VBox { spacing = 6; children = List(assignmentsLabel, assignmentsList, assignmentsDetailsPane) }

  val northPane = new HBox { spacing = 6; children = List(studentGradePane) }
  val centerPane = new Separator()
  val southPane = new HBox { spacing = 6; children = List(coursesPane, assignmentsPane) }
  val contentPane = new VBox { spacing = 6; padding = Insets(6); prefWidth = 600; children = List(northPane, centerPane, southPane) }
  stage = new JFXApp.PrimaryStage {
    scene = new Scene { root = contentPane }
  }
}