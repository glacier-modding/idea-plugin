package org.thepeacockproject.idea.ui;

import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class AlwaysShownJBScrollPane extends JBScrollPane {
    public AlwaysShownJBScrollPane(final @NotNull Component view) {
        super(view);
        this.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        );
        this.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
    }
}
