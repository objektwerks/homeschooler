package hs

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

final class RepositoryTest extends AnyFunSuite:
  test("repository"):
    val config = DatabaseConfig.forConfig[JdbcProfile]("test", ConfigFactory.load("test.conf"))
    val repository = Repository(config)
    
    import repository.*

    repository.createSchema()

    repository.await( students.list() )

    repository.dropSchema()
    repository.close()