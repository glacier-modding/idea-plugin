package org.thepeacockproject.idea.intelligence

import com.intellij.openapi.fileEditor.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull

class JsonFileEditorProvider implements FileEditorProvider {
    @Override
    boolean accept(@NotNull final Project project, @NotNull final VirtualFile file) {
        return file.getExtension() != null && file.getExtension() == "json"
    }

    @Override
    @NotNull
    FileEditor createEditor(@NotNull final Project project, @NotNull final VirtualFile file) {
        return new MenuFileEditor(file)
    }

    @Override
    @NotNull
    @NonNls
    String getEditorTypeId() {
        return "GlacierJsonEditor"
    }

    @Override
    @NotNull
    FileEditorPolicy getPolicy() {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }
}
