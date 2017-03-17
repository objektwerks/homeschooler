package hs.view

import scalafx.application.Platform
import scalafx.scene.control.{Menu, MenuBar, MenuItem, SeparatorMenuItem}

object MenuView {
  val loadTestDataMenuItem = new MenuItem("Load Test Data")
  val separatorMenuItem = new SeparatorMenuItem()
  val exitMenuItem = new MenuItem("Exit") { onAction = { _ => Platform.exit() } }
  val fileMenu = new Menu("File") { items = List(loadTestDataMenuItem, separatorMenuItem, exitMenuItem) }
  val menuBar = new MenuBar { menus = List(fileMenu); useSystemMenuBar = true }
}