package org.thepeacockproject.idea.settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PeacockSettingsConfigurable implements Configurable {
    private PeacockSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Peacock Plugin";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new PeacockSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        final PeacockSettingsState settings = PeacockSettingsState.getInstance();

        return !settingsComponent.getRepositoryText().equals(settings.repository);
    }

    @Override
    public void apply() {
        final PeacockSettingsState settings = PeacockSettingsState.getInstance();
        settings.repository = settingsComponent.getRepositoryText();
    }

    @Override
    public void reset() {
        final PeacockSettingsState settings = PeacockSettingsState.getInstance();
        settingsComponent.setRepositoryText(settings.repository);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
