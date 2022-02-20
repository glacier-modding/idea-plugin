package org.thepeacockproject.idea.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBTextArea;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class Crc32Dialog extends DialogWrapper {
    public Crc32Dialog(@Nullable final Project project) {
        super(project, false);
        setTitle("CRC32 Calculator");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        final Dimension textSize = new Dimension(500, 200);
        final JPanel dialogPanel = new JPanel();

        final JLabel labelInput = new JLabel("Input:");
        dialogPanel.add(labelInput);

        final JBTextArea input = new JBTextArea();
        input.setPreferredSize(textSize);

        dialogPanel.add(input);

        final JLabel labelOutput = new JLabel("Output:");
        dialogPanel.add(labelOutput);

        final JBTextArea output = new JBTextArea();
        output.setEditable(false);
        output.setPreferredSize(textSize);

        dialogPanel.add(output);

        input.addKeyListener((IKeyTypedListener) e -> {
            final List<Long> targetValues = Arrays.stream(input.getText().split("\n")).map(Crc32Dialog::getCrc32).collect(Collectors.toList());
            final StringBuilder result = new StringBuilder();

            for (long target : targetValues) {
                result.append(target).append("\n");
            }

            output.setText(result.toString());
        });

        return dialogPanel;
    }

    private static long getCrc32(@NotNull final String input) {
        final CRC32 crc32 = new CRC32();

        crc32.update(input.getBytes());

        return crc32.getValue();
    }
}
