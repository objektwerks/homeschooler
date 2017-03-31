package hs.view

import com.typesafe.config.Config
import hs.model.Model
import hs.pane._

import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.SplitPane
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, Priority, VBox}

class View(conf: Config, model: Model) {
  val menuPane = new MenuPane()
  val studentPane = new StudentPane(conf, model)
  val gradePane = new GradePane(conf, model)
  val coursePane = new CoursePane(conf, model)
  val assignmentPane = new AssignmentPane(conf, model)

  val westPane = new VBox { spacing = 6; padding = Insets(6); children = List(studentPane, gradePane) }
  val eastPane = new VBox { spacing = 6; padding = Insets(6); children = List(coursePane, assignmentPane) }
  val splitPane = new SplitPane { vgrow = Priority.Always; hgrow = Priority.Always; padding = Insets(6); items.addAll(westPane, eastPane) }
  val contentPane = new HBox { spacing = 6; padding = Insets(6); children = List(menuPane, splitPane) }
  val sceneGraph = new Scene { root = contentPane }

  model.listStudents()
}

object View {
  def appImmage() = new Image(View.getClass.getResourceAsStream("/homeschool.png"))

  def addImageView() = loadImageView("/add.png")

  def editImageView() = loadImageView("/edit.png")

  def loadImageView(path: String) = new ImageView {
    image = new Image(View.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }
}