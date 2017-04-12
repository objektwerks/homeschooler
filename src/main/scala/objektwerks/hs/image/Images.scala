package objektwerks.hs.image

import scalafx.scene.image.{Image, ImageView}

object Images {
  def appImage(): Image = new Image(Images.getClass.getResourceAsStream("/homeschool.png"))

  def addImageView(): ImageView = loadImageView("/add.png")

  def editImageView(): ImageView = loadImageView("/edit.png")

  def barChartImageView(): ImageView = loadImageView("/bar.chart.png")

  def lineChartImageView(): ImageView = loadImageView("/line.chart.png")

  def loadImageView(path: String): ImageView = new ImageView {
    image = new Image(Images.getClass.getResourceAsStream(path))
    fitHeight = 25
    fitWidth = 25
    preserveRatio = true
    smooth = true
  }
}