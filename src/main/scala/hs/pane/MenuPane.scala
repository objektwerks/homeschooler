package hs.pane

import scalafx.application.Platform
import scalafx.scene.control.{Menu, MenuBar, MenuItem}

class MenuPane extends MenuBar {
  val exitMenuItem = new MenuItem("Exit") { onAction = { _ => Platform.exit() } }
  val fileMenu = new Menu("File") { items = List(exitMenuItem) }

  menus = List(fileMenu)
  useSystemMenuBar = true
}