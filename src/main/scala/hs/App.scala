package hs

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import java.awt.{Taskbar, Toolkit}
import java.awt.Taskbar.Feature

import scalafx.application.JFXApp3

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object App extends JFXApp3 with LazyLogging:
  logger.info("Starting Homeschool app ...")

  val context = Context( ConfigFactory.load("app.conf") )
  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("repository", ConfigFactory.load("repository.conf"))
  val repository = Repository(dbConfig).ifAbsentInstall()
  val model = Model(repository)

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      scene = View(context, model).scene
      title = context.title
      minHeight = context.height.toDouble
      minWidth = context.width.toDouble
      icons += context.appImage

    if Taskbar.isTaskbarSupported() then
      val taskbar = Taskbar.getTaskbar()
      if taskbar.isSupported(Feature.ICON_IMAGE) then
        val appIcon = Toolkit.getDefaultToolkit.getImage(this.getClass().getResource("/images/icon.png"))
        taskbar.setIconImage(appIcon)

    model.listStudents()
    stage.show()
    logger.info("Homeschool app sarted.")

  override def stopApp(): Unit =
    repository.close()
    logger.info("Homeschool app stopped.")