package hs

import com.typesafe.config.ConfigFactory
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

import scalafx.application.JFXApp
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

object App extends JFXApp {
  val config = DatabaseConfig.forConfig[JdbcProfile]("app", ConfigFactory.load("app.conf"))
  val repository = new Repository(config = config, profile = H2Profile)

  val contentPane = new VBox {
    prefWidth = 600
    prefHeight = 600
    spacing = 6
    padding = Insets(6)
    children = List()
  }

  stage = new JFXApp.PrimaryStage {
    scene = new Scene {
      root = contentPane
    }
  }
}