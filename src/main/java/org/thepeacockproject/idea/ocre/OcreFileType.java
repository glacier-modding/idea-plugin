package org.thepeacockproject.idea.ocre;

import com.intellij.icons.AllIcons;
import com.intellij.json.JsonFileType;
import com.intellij.json.JsonLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class OcreFileType extends JsonFileType {
    protected OcreFileType() {
        super(JsonLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "OCRE";
    }

    @Override
    public @NotNull String getDescription() {
        return "OpenContracts Registry Entry (OCRE) file.";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return ".ocre";
    }

    @Override
    public @Nullable Icon getIcon() {
        return AllIcons.FileTypes.Json;
    }

    @Override
    public boolean isReadOnly() {
        return super.isReadOnly();
    }
}
