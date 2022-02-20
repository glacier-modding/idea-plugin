package org.thepeacockproject.idea.ui;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.thepeacockproject.idea.repo.GameRepositoryEntry;
import org.thepeacockproject.idea.repo.RepositoryManager;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepositoryToolWindow implements ToolWindowFactory {
    private static final Logger LOGGER = Logger.getInstance("PeacockRepositoryToolWindow");
    private final ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
    private JTextArea results;
    private final Map<String, String> repoEntries = new ConcurrentHashMap<>();

    @Override
    public void createToolWindowContent(final @NotNull Project project, @NotNull final ToolWindow toolWindow) {
        final LongerJBTextField searchBar = new LongerJBTextField();

        searchBar.setSize(searchBar.getWidth() * 4, searchBar.getHeight());

        this.results = new JTextArea();
        this.results.setText("Waiting for search input...");
        this.results.setEditable(false);

        searchBar.addActionListener(actionEvent ->
                updateResultsByQuery(searchBar.getText().toLowerCase(Locale.ENGLISH))
        );

        final JScrollPane scrollableContainer = new AlwaysShownJBScrollPane(results);

        final JPanel searchPanel = new JPanel();

        searchPanel.add(new JBLabel("Search query: "));
        searchPanel.add(searchBar);

        final JPanel rootPanel = new JPanel();

        rootPanel.add(searchPanel);
        rootPanel.add(scrollableContainer);

        rootPanel.setLayout(new GridLayout(1, 2));
        rootPanel.setBorder(JBUI.Borders.empty(10));

        toolWindow.getComponent().add(rootPanel);
    }

    private void refreshRepository() {
        this.repoEntries.clear();

        final GameRepositoryEntry[] entries = RepositoryManager.getInstance().getEntries();

        if (entries == null) {
            return;
        }

        for (GameRepositoryEntry entry : entries) {
            if (entry.name == null) {
                continue;
            }

            this.repoEntries.put(entry.id, entry.name);
        }
    }

    private void updateResultsByQuery(final String query) {
        this.results.setText("Here's what I found:\n");

        for (Map.Entry<String, String> entry : this.repoEntries.entrySet()) {
            if (entry.getValue().toLowerCase(Locale.ENGLISH).startsWith(query)) {
                this.results.setText(
                        this.results.getText()
                                + "\n"
                                + entry.getKey()
                                + ": "
                                + entry.getValue()
                );
            }
        }

        LOGGER.info("Search results ready.");
    }

    @Override
    public void init(@NotNull final ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);

        // offload this to another thread, so it doesn't freeze the UI
        this.threadExecutor.submit(this::refreshRepository);
    }

    private static class LongerJBTextField extends JBTextField {
        @Override
        public @NotNull Dimension getPreferredSize() {
            final Dimension baseSize = super.getPreferredSize();

            return new Dimension(baseSize.width * 4, baseSize.height);
        }
    }
}
