package objektwerks.hs

import scalafx.scene.image.{Image, ImageView}

object Resources {
  def appImage = new Image(Resources.getClass.getResourceAsStream("/images/homeschool.png"))
  def addImageView = loadImageView("/images/add.png")
  def editImageView = loadImageView("/images/edit.png")
  def barChartImageView = loadImageView("/images/bar.chart.png")
  def lineChartImageView = loadImageView("/images/line.chart.png")

  def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Resources.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }
}