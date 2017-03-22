package hs.pane

import scalafx.geometry.Insets
import scalafx.scene.control.{Control, Label}
import scalafx.scene.layout.GridPane

class ControlGridPane(controls: Map[String, Control]) extends GridPane {
  hgap = 6
  vgap = 6
  padding = Insets(top = 6, right = 100, bottom = 6, left = 6)
  for(row <- 0 until controls.size; (label, control) <- controls) {
    add(new Label { text = label }, columnIndex = 0, rowIndex = row)
    add(child = control, columnIndex = 1, rowIndex = row)
  }
}