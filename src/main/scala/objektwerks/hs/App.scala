package objektwerks.hs

import com.typesafe.config.ConfigFactory

import objektwerks.hs.model.Model
import objektwerks.hs.repository.Repository
import objektwerks.hs.view.View

import scalafx.application.JFXApp

object App extends JFXApp {
  val conf = ConfigFactory.load("app.conf")
  val repository = Repository("repository.conf")
  val model = Model(repository)
  val view = View(conf, model)
  
  stage = new JFXApp.PrimaryStage {
    scene = view.sceneGraph
    title = conf.getString("title")
    minHeight = conf.getInt("height").toDouble
    minWidth = conf.getInt("width").toDouble
    icons.add(Resources.appImage)
  }

  sys.addShutdownHook {
    repository.close()
  }
}