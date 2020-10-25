package objektwerks.hs

import com.typesafe.config.ConfigFactory
import objektwerks.hs.image.Images
import objektwerks.hs.model.Model
import objektwerks.hs.repository.Repository
import objektwerks.hs.view.View
import scalafx.application.JFXApp

object App extends JFXApp {
  val conf = ConfigFactory.load("app.conf")
  val repository = Repository("repository.conf")
  val model = new Model(repository)
  val view = new View(conf, model)
  stage = new JFXApp.PrimaryStage {
    scene = view.sceneGraph
    title = conf.getString("title")
    minHeight = conf.getInt("height").toDouble
    minWidth = conf.getInt("width").toDouble
    icons.add(Images.appImage())
  }

  sys.addShutdownHook {
    repository.close()
  }
}