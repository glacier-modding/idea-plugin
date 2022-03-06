package org.thepeacockproject.idea.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBLabel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thepeacockproject.idea.trees.GTreeCreator;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MenuFileEditor implements FileEditor {
    private final VirtualFile file;
    private final JComponent editor;

    public MenuFileEditor(@NotNull final VirtualFile virtualFile) throws IOException {
        this.file = virtualFile;
        this.editor = createPanel();
    }

    @Nullable
    protected JComponent createPanel() throws IOException {
        return GTreeCreator.createRecursiveTreeFrom(new String(this.file.contentsToByteArray(), StandardCharsets.UTF_8));
    }

    @Override
    @NotNull
    public JComponent getComponent() {
        if (this.editor == null) {
            return new JBLabel("Something went wrong, try again later please!");
        }

        return this.editor;
    }

    @Override
    @Nullable
    public JComponent getPreferredFocusedComponent() {
        return this.editor;
    }

    @Override
    @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull
    public String getName() {
        return "Glacier Menu Editor";
    }

    @Override
    public void setState(@NotNull final FileEditorState state) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addPropertyChangeListener(@NotNull final PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull final PropertyChangeListener listener) {

    }

    @Override
    @Nullable
    public FileEditorLocation getCurrentLocation() {
        final MenuFileEditor this$MenuFileEditor = this;

        return new FileEditorLocation() {
            @Override
            @NotNull
            public FileEditor getEditor() {
                return this$MenuFileEditor;
            }

            @Override
            public int compareTo(@NotNull final FileEditorLocation o) {
                return 0;
            }
        };
    }

    @Override
    public void dispose() {

    }

    @Override
    @Nullable
    public <T> T getUserData(@NotNull final Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull final Key<T> key, @Nullable final T value) {

    }

    @Override
    @NotNull
    public VirtualFile getFile() {
        return this.file;
    }
}
