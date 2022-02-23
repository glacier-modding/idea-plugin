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
        storages = @Storage("GlacierPlugin.xml")
)
public class GlacierSettingsState implements PersistentStateComponent<GlacierSettingsState> {
    public String repository = "";

    public static GlacierSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(GlacierSettingsState.class);
    }

    @Nullable
    @Override
    public GlacierSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull final GlacierSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
