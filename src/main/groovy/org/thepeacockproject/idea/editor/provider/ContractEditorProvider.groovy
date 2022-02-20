package org.thepeacockproject.idea.editor.provider

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import org.thepeacockproject.idea.editor.ContractFileEditor

@CompileStatic
class ContractEditorProvider implements FileEditorProvider {
    @Override
    boolean accept(@NotNull final Project project, @NotNull final VirtualFile file) {
        return file.getExtension() != null && file.getExtension() == "ocre"
    }

    @Override
    @NotNull
    FileEditor createEditor(@NotNull final Project project, @NotNull final VirtualFile file) {
        return new ContractFileEditor(file)
    }

    @Override
    @NotNull
    @NonNls
    String getEditorTypeId() {
        return "GlacierContractEditor"
    }

    @Override
    @NotNull
    FileEditorPolicy getPolicy() {
        return FileEditorPolicy.PLACE_BEFORE_DEFAULT_EDITOR
    }
}
