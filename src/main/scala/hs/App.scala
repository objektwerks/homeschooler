package objektwerks.hs

import com.typesafe.config.ConfigFactory

import objektwerks.hs.model.Model
import objektwerks.hs.repository.Repository
import objektwerks.hs.view.View

import scalafx.application.JFXApp3

object App extends JFXApp3 {
  val conf = ConfigFactory.load("app.conf")
  val repository = Repository("repository.conf")
  val model = Model(repository)
  val view = View(conf, model)


  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      scene = view.sceneGraph
      title = conf.getString("title")
      minHeight = conf.getInt("height").toDouble
      minWidth = conf.getInt("width").toDouble
      icons.add(Resources.appImage)
    }
  }
  
  sys.addShutdownHook {
    repository.close()
  }
}