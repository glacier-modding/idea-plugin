package org.thepeacockproject.idea.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface IKeyTypedListener extends KeyListener {
    @Override
    default void keyPressed(final KeyEvent e) {
        this.keyTyped(e);
    }

    @Override
    default void keyReleased(final KeyEvent e) {
        this.keyTyped(e);
    }
}
