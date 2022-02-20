package org.thepeacockproject.idea.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "org.thepeacockproject.idea",
        storages = @Storage("PeacockPlugin.xml")
)
public class PeacockSettingsState implements PersistentStateComponent<PeacockSettingsState> {
    public String repository = "";

    public static PeacockSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(PeacockSettingsState.class);
    }

    @Nullable
    @Override
    public PeacockSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull final PeacockSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}