package objektwerks.hs.dialog

import java.time.format.DateTimeFormatter

import com.typesafe.config.Config
import objektwerks.hs.App
import objektwerks.hs.entity.Assignment

import scalafx.Includes._
import scalafx.geometry.Pos
import scalafx.scene.chart.{LineChart, NumberAxis, XYChart}
import scalafx.scene.control.{ButtonType, Dialog, Label}
import scalafx.scene.layout.{HBox, VBox}

class AssignmentChartDialog(conf: Config, assignments: List[Assignment]) extends Dialog[Unit] {
  val xAxis = NumberAxis(axisLabel = conf.getString("chart-months"), lowerBound = 1, upperBound = 12, tickUnit = 1)
  val yAxis = NumberAxis(axisLabel = conf.getString("chart-scores"), lowerBound = 0, upperBound = 100, tickUnit = 10)
  val chart = LineChart[Number, Number](xAxis, yAxis)
  val series = new XYChart.Series[Number, Number]{ name = conf.getString("chart-score") }
  val dateFormatter = DateTimeFormatter.ofPattern("MM.dd")
  assignments foreach { assignment =>
    series.data() += XYChart.Data[Number, Number]( assignment.completed.format(dateFormatter).toDouble, assignment.score.toInt )
  }
  chart.data = series

  val minScoreLabel = new Label { text = conf.getString("min-score") }
  val minScore = new Label { text = assignments.map(a => a.score).min.toInt.toString }
  val maxScoreLabel = new Label { text = conf.getString("max-score") }
  val maxScore = new Label { text = assignments.map(a => a.score).max.toInt.toString }
  val scoreLabel = new Label { text = conf.getString("score") }
  val score = new Label { text = (assignments.map(a => a.score).sum / assignments.length).toInt.toString }

  val scoreBox = new HBox { alignment = Pos.Center; spacing = 6; children = List(minScoreLabel, minScore, maxScoreLabel, maxScore, scoreLabel, score) }
  val chartBox = new VBox { spacing = 6; children = List(chart, scoreBox) }

  val dialog = dialogPane()
  dialog.buttonTypes = List(ButtonType.Close)
  dialog.content = chartBox

  initOwner(App.stage)
  title = conf.getString("assignment-chart")
  headerText = conf.getString("assignment-scores")
}