package org.thepeacockproject.idea.intelligence

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import groovy.transform.CompileStatic
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.thepeacockproject.idea.trees.GTreeCreator

import javax.swing.JComponent
import java.beans.PropertyChangeListener
import java.nio.charset.StandardCharsets

@CompileStatic
class MenuFileEditor implements FileEditor {
    private final VirtualFile file
    private final JComponent editor

    MenuFileEditor(@NotNull final VirtualFile virtualFile) {
        this.file = virtualFile
        this.editor = createPanel()
    }

    @Nullable
    JComponent createPanel() {
        return GTreeCreator.createRecursiveTreeFrom(new String(this.file.contentsToByteArray(), StandardCharsets.UTF_8))
    }

    @Override
    @NotNull
    JComponent getComponent() {
        return this.editor
    }

    @Override
    @Nullable
    JComponent getPreferredFocusedComponent() {
        return this.editor
    }

    @Override
    @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull
    String getName() {
        return "Glacier Menu Editor"
    }

    @Override
    void setState(@NotNull final FileEditorState state) {

    }

    @Override
    boolean isModified() {
        return false
    }

    @Override
    boolean isValid() {
        return true
    }

    @Override
    void addPropertyChangeListener(@NotNull final PropertyChangeListener listener) {

    }

    @Override
    void removePropertyChangeListener(@NotNull final PropertyChangeListener listener) {

    }

    @Override
    @Nullable
    FileEditorLocation getCurrentLocation() {
        final MenuFileEditor this$MenuFileEditor = this

        return new FileEditorLocation() {
            @Override
            FileEditor getEditor() {
                return this$MenuFileEditor
            }

            @Override
            int compareTo(@NotNull final FileEditorLocation o) {
                return 0
            }
        }
    }

    @Override
    void dispose() {

    }

    @Override
    @Nullable
    <T> T getUserData(@NotNull final Key<T> key) {
        return null
    }

    @Override
    <T> void putUserData(@NotNull final Key<T> key, @Nullable final T value) {

    }

    @Override
    @NotNull
    VirtualFile getFile() {
        return this.file
    }
}
