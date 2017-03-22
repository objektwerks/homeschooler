package hs.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{Control, Label}
import scalafx.scene.layout.GridPane

class DialogGridPane(controls: Map[String, Control]) extends GridPane {
  hgap = 6
  vgap = 6
  padding = Insets(6, 100, 6, 6)
  for(row <- 0 until controls.size; (label, control) <- controls) {
    add(new Label(label), columnIndex = 0, rowIndex = row)
    add(control, columnIndex = 1, rowIndex = row)
  }
}