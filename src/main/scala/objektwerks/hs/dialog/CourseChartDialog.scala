package objektwerks.hs.dialog

import com.typesafe.config.Config
import objektwerks.hs.entity.Course

import scalafx.scene.chart.CategoryAxis
import scalafx.scene.control.Dialog

class CourseChartDialog(conf: Config, courses: List[Course]) extends Dialog[Unit] {
  val xAxis = CategoryAxis(courses.map(c => c.name))
}