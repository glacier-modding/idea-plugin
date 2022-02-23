package org.thepeacockproject.idea.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class GlacierSettingsConfigurable implements Configurable {
    private GlacierSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Glacier² Plugin";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new GlacierSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        final GlacierSettingsState settings = GlacierSettingsState.getInstance();

        return !settingsComponent.getRepositoryText().equals(settings.repository);
    }

    @Override
    public void apply() {
        final GlacierSettingsState settings = GlacierSettingsState.getInstance();
        settings.repository = settingsComponent.getRepositoryText();
    }

    @Override
    public void reset() {
        final GlacierSettingsState settings = GlacierSettingsState.getInstance();
        settingsComponent.setRepositoryText(settings.repository);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
