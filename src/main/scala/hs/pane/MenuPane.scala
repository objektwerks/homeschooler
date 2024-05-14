package hs.pane

import scalafx.application.Platform
import scalafx.scene.control.{Alert, Menu, MenuBar, MenuItem, SeparatorMenuItem}
import scalafx.scene.control.Alert.AlertType

import hs.{App, Context}

class MenuPane(context: Context) extends MenuBar:
  val aboutDialog = new Alert(AlertType.Information):
    initOwner(App.stage)
    title = context.getString("about")
    headerText = context.getString("developer")
    contentText = s"${context.getString("app")} ${context.getString("license")}"

  val aboutMenuItem = new MenuItem(context.getString("about")):
    onAction = { _ => aboutDialog.showAndWait() }

  val separator = SeparatorMenuItem()

  val exitMenuItem = new MenuItem(context.getString("exit")):
    onAction = { _ => Platform.exit() }
  
  val menu = new Menu(context.getString("menu")):
    items = List(aboutMenuItem, separator, exitMenuItem)

  menus = List(menu)
  useSystemMenuBar = false