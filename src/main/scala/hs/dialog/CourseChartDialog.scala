package hs.dialog

import scalafx.Includes.*
import scalafx.collections.ObservableBuffer
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}
import scalafx.scene.control.{ButtonType, Dialog}
import scalafx.scene.layout.VBox

import hs.{App, Context, Course, Model}

class CourseChartDialog(context: Context,
                        courses: ObservableBuffer[Course],
                        model: Model) extends Dialog[Unit]:
  val xAxis = CategoryAxis(courses.map(c => c.name).distinct)
  xAxis.label = context.courseChartCourses

  val yAxis = NumberAxis(axisLabel = context.courseChartScores, lowerBound = 0, upperBound = 100, tickUnit = 10)
  
  val chart = BarChart[String, Number](xAxis, yAxis)
  chart.categoryGap = 25.0

  courses foreach { course =>
    val series = new XYChart.Series[String, Number]:
      name = course.name
      data() += XYChart.Data[String, Number](course.name, model.scoreCourse(course.id))
    chart.data() += series
  }
  
  val chartBox = new VBox:
    spacing = 6
    children = List(chart)

  val dialog = dialogPane()
  dialog.buttonTypes = List(ButtonType.Close)
  dialog.content = chartBox

  initOwner(App.stage)
  title = conf.getString("course-chart")
  headerText = conf.getString("course-scores")