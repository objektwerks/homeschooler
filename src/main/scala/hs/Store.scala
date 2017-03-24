package hs

import com.typesafe.config.ConfigFactory
import hs.repository.Repository
import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

object Store {
  val config = DatabaseConfig.forConfig[JdbcProfile]("app", ConfigFactory.load("app.conf"))
  val repository = new Repository(config = config, profile = H2Profile)
}