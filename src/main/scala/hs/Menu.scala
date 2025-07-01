package hs

import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, Menu => MenuRoot, MenuBar, MenuItem, SeparatorMenuItem, TextArea}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.layout.GridPane

final class Menu(context: Context) extends MenuBar:
  val textAreaAbout = new TextArea():
    editable = false
    wrapText = true
    text = context.contentText

  val gridPaneAbout = new GridPane():
    prefHeight = 120
    prefWidth = 200
  gridPaneAbout.add(textAreaAbout, 0, 0)

  val menuItemAbout = new MenuItem:
    text = context.about
    onAction = (_: ActionEvent) =>
      new Alert(AlertType.Information):
        initOwner(App.stage)
        title = context.title
        headerText = context.headerText
        dialogPane().content = gridPaneAbout
      .showAndWait()

  val menuItemExit = new MenuItem:
    text = context.exit
    onAction = (_: ActionEvent) => Platform.exit()

  val menuRoot = new MenuRoot():
    text = context.menu
    items = List(menuItemAbout, SeparatorMenuItem(), menuItemExit)

  menus = List(menuRoot)