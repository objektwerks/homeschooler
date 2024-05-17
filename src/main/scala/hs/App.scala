package hs

import com.typesafe.config.ConfigFactory

import scalafx.application.JFXApp3

import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

object App extends JFXApp3:
  val context = Context( ConfigFactory.load("app.conf") )

  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("repository", ConfigFactory.load("repository.conf"))
  val repository = Repository(dbConfig, H2Profile).verify()
  val model = Model(repository)

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      scene = View(context, model).scene
      title = context.title
      minHeight = context.height.toDouble
      minWidth = context.width.toDouble
      icons.add( context.appImage )

    model.listStudents()
    stage.show()
    println("*** Homeschool app started.")

  override def stopApp(): Unit =
    repository.close()
    println("*** Homeschool app stopped.")