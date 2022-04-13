package hs

import com.typesafe.config.ConfigFactory

import scalafx.application.JFXApp3

object App extends JFXApp3 {
  val resources = ConfigFactory.load("resources.conf")
  val repository = Repository("repository.conf")
  val model = Model(repository)
  val view = View(resources, model)


  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      scene = view.sceneGraph
      title = resources.getString("title")
      minHeight = resources.getInt("height").toDouble
      minWidth = resources.getInt("width").toDouble
      icons.add(Images.appImage)
    }
  }
  
  sys.addShutdownHook {
    repository.close()
  }
}