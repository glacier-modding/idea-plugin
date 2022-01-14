package org.thepeacockproject.idea.repo;

import com.intellij.icons.AllIcons;
import com.intellij.json.JsonFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GameRepositoryFileType extends JsonFileType {
    @Override
    public @NotNull String getName() {
        return "REPO";
    }

    @Override
    public @NotNull String getDescription() {
        return "Glacier repository";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "REPO";
    }

    @Override
    public @Nullable Icon getIcon() {
        return AllIcons.Debugger.VariablesTab;
    }
}
