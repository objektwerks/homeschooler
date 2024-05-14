package hs

import com.typesafe.config.ConfigFactory

import scalafx.application.JFXApp3

import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

object App extends JFXApp3:
  val config = ConfigFactory.load("repository.conf")
  val context = Context(config)

  val dbConfig = DatabaseConfig.forConfig[JdbcProfile]("repository", ConfigFactory.load("repository.conf"))
  val repository = Repository(dbConfig, H2Profile)
  val model = Model(repository)

  val view = View(context, model)

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      scene = view.sceneGraph
      title = context.title
      minHeight = context.height.toDouble
      minWidth = context.width.toDouble
      icons.add( context.appImage )
  
  sys.addShutdownHook:
    repository.close()