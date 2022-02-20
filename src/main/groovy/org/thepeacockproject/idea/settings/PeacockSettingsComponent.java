package org.thepeacockproject.idea.settings;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PeacockSettingsComponent {
    private final JPanel mainPanel;
    private final JBTextField repositoryText = new JBTextField();

    public PeacockSettingsComponent() {
        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Paste the game's repository (.REPO file) here: "), repositoryText, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return repositoryText;
    }

    @NotNull
    public String getRepositoryText() {
        return repositoryText.getText();
    }

    public void setRepositoryText(@NotNull final String newText) {
        repositoryText.setText(newText);
    }
}
