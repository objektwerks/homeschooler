package hs

import com.typesafe.config.ConfigFactory
import hs.model.Model
import hs.repository.Repository
import hs.view.View

import scalafx.application.JFXApp

object App extends JFXApp {
  val conf = ConfigFactory.load("app.conf")
  val repository = Repository.newInstance("repository.conf")
  val model = new Model(repository)
  val view = new View(conf, model)
  stage = new JFXApp.PrimaryStage { scene = view.sceneGraph; title = conf.getString("title"); minHeight = 616; maxHeight = 616; minWidth = 684; maxWidth = 684 }

  sys.addShutdownHook { repository.close() }
}