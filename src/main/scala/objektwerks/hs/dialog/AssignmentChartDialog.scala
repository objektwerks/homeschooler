package objektwerks.hs.dialog

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.typesafe.config.Config
import objektwerks.hs.App
import objektwerks.hs.entity.Assignment
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import scalafx.scene.control.{ButtonType, Dialog, Label}
import scalafx.scene.layout.{HBox, VBox}

class AssignmentChartDialog(conf: Config, assignments: ObservableBuffer[Assignment]) extends Dialog[Unit] {
  implicit def localDateOrdering: Ordering[LocalDate] = Ordering.by(_.toEpochDay)

  val dateFormatter = DateTimeFormatter.ofPattern("yy.D")
  val minDate = assignments.map(a => a.completed).min.format(dateFormatter).toDouble
  val maxDate = assignments.map(a => a.completed).max.format(dateFormatter).toDouble

  val xAxis = NumberAxis(axisLabel = s"${conf.getString("assignment-chart-months")} [$minDate - $maxDate]", lowerBound = minDate, upperBound = maxDate, tickUnit = 1)
  val yAxis = NumberAxis(axisLabel = conf.getString("assignment-chart-scores"), lowerBound = 0, upperBound = 100, tickUnit = 10)
  val chart = LineChart[Number, Number](xAxis, yAxis)
  chart.padding = Insets(6)

  val series = new XYChart.Series[Number, Number] {
    name = conf.getString("assignment-chart-score")
  }
  assignments foreach { assignment =>
    series.data() += XYChart.Data[Number, Number](assignment.completed.format(dateFormatter).toDouble, assignment.score.toInt)
  }
  chart.data = series

  val minScoreLabel = new Label {
    text = conf.getString("min-score")
  }
  val minScore = new Label {
    text = assignments.map(a => a.score).min.toInt.toString
  }
  val maxScoreLabel = new Label {
    text = conf.getString("max-score")
  }
  val maxScore = new Label {
    text = assignments.map(a => a.score).max.toInt.toString
  }
  val scoreLabel = new Label {
    text = conf.getString("score")
  }
  val score = new Label {
    text = (assignments.map(a => a.score).sum / assignments.length).toInt.toString
  }

  val scoreBox = new HBox {
    alignment = Pos.Center;
    spacing = 6;
    children = List(minScoreLabel, minScore, maxScoreLabel, maxScore, scoreLabel, score)
  }
  val chartBox = new VBox {
    spacing = 6;
    children = List(chart, scoreBox)
  }

  val dialog = dialogPane()
  dialog.buttonTypes = List(ButtonType.Close)
  dialog.content = chartBox

  initOwner(App.stage)
  title = conf.getString("assignment-chart")
  headerText = conf.getString("assignment-scores")
}