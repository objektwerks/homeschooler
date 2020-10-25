package objektwerks.hs.image

import scalafx.scene.image.{Image, ImageView}

object Images {
  val appImage = new Image(Images.getClass.getResourceAsStream("/images/homeschool.png"))
  val addImageView = loadImageView("/images/add.png")
  val editImageView = loadImageView("/images/edit.png")
  val barChartImageView = loadImageView("/images/bar.chart.png")
  val lineChartImageView = loadImageView("/images/line.chart.png")

  def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Images.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }
}