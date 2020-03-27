package com.demkom58.nmlab2.util;

import javafx.scene.input.KeyEvent;

public interface CellUpdateCallback {
    void onCellUpdate(KeyEvent event, int x, int y);
}