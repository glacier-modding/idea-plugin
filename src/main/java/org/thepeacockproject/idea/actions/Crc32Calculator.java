package org.thepeacockproject.idea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.thepeacockproject.idea.ui.Crc32Dialog;

public class Crc32Calculator extends AnAction {
    @Override
    public void actionPerformed(final @NotNull AnActionEvent e) {
        new Crc32Dialog(e.getProject()).show();
    }
}
