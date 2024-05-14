package hs

import com.typesafe.config.Config

import scala.jdk.CollectionConverters.*

import scalafx.scene.image.{Image, ImageView}

final class Context(config: Config):
  val assignmentChartMonths = config.getString("assignment-chart-months")
  val assignmentChartScores = config.getString("assignment-chart-scores")
  val assignmentChartScorfe = config.getString("assignment-chart-score")
  val minScore = config.getString("min-score")
  val maxScore = config.getString("max-score")
  val score = config.getString("score")

  def appImage = Image( Images.getClass.getResourceAsStream("/images/homeschool.png") )

  def addImageView = loadImageView("/images/add.png")

  def editImageView = loadImageView("/images/edit.png")

  def barChartImageView = loadImageView("/images/bar.chart.png")

  def lineChartImageView = loadImageView("/images/line.chart.png")

  def loadImageView(path: String): ImageView = new ImageView:
    image = Image( Images.getClass.getResourceAsStream(path) )
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true