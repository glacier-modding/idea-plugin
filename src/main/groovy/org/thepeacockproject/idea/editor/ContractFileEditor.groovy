package org.thepeacockproject.idea.editor

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import groovy.transform.CompileStatic
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.thepeacockproject.idea.editor.internal.WithCheckBox
import org.thepeacockproject.idea.editor.internal.WithContractType
import org.thepeacockproject.idea.editor.internal.WithTextInput
import org.thepeacockproject.idea.structs.GlacierContract
import org.thepeacockproject.idea.structs.GlacierContractData
import org.thepeacockproject.idea.structs.GlacierContractMetadata

import javax.swing.JCheckBox
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import java.beans.PropertyChangeListener
import java.lang.reflect.Field
import java.nio.charset.StandardCharsets

@CompileStatic
class ContractFileEditor implements FileEditor {
    private static final Gson GSON
    private final VirtualFile file
    private JPanel mainPanel
    private GlacierContract activeEditingContract

    static {
        GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    }

    ContractFileEditor(@NotNull final VirtualFile virtualFile) {
        this.file = virtualFile
        this.initForm()
    }

    void parse() {
        final def s = new String(this.file.contentsToByteArray())

        this.activeEditingContract = GSON.fromJson(s, GlacierContract.class)
    }

    void pushChanges() {
        final def s = GSON.toJson(this.activeEditingContract)

        ApplicationManager.getApplication().runWriteAction {
            final long t = System.currentTimeMillis()

            this.file.setBinaryContent(s.getBytes(StandardCharsets.UTF_8), t, t)
        }
    }

    @Override
    JComponent getComponent() {
        return this.mainPanel
    }

    @Override
    JComponent getPreferredFocusedComponent() {
        return this.mainPanel
    }

    @Override
    @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull
    String getName() {
        return "Contract File Editor"
    }

    @Override
    void setState(@NotNull FileEditorState state) {

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
    void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Override
    void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Override
    FileEditorLocation getCurrentLocation() {
        return null
    }

    @Override
    void dispose() {

    }

    @Override
    <T> T getUserData(@NotNull Key<T> key) {
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

    void initForm() {
        parse()
        // pushChanges()

        List<FormBuilderHolder> listOfItems = []

        final def closure = { Field it, boolean isMetadata ->
            final def wti = it.getAnnotation(WithTextInput.class)
            final def wcb = it.getAnnotation(WithCheckBox.class)
            final def wct = it.getAnnotation(WithContractType.class)

            def value

            try {
                final def holder = isMetadata ? this.activeEditingContract.metadata : this.activeEditingContract.data

                value = holder.getProperty(it.getName())
            } catch (MissingPropertyException ignored) {
                value = null
            }

            if (wti != null) {
                final FormBuilderHolder h = new FormBuilderHolder(
                        label: new JBLabel(wti.value()),
                        item: new JTextField(value as String)
                )

                listOfItems.add(h)
            }

            if (wcb != null) {
                if (value == null) {
                    value = false
                }

                final FormBuilderHolder h = new FormBuilderHolder(
                        label: new JBLabel(wcb.value()),
                        item: new JCheckBox("", value as boolean)
                )

                listOfItems.add(h)
            }

            if (wct != null) {
                final FormBuilderHolder h = new FormBuilderHolder(
                        label: new JBLabel("Contract type: "),
                        item: new JComboBox<String>(wct.value())
                )

                listOfItems.add(h)
            }
        }

        GlacierContractMetadata.getDeclaredFields().each {
            closure(it, true)
        }
        GlacierContractData.getDeclaredFields().each {
            closure(it, false)
        }

        final FormBuilder f = FormBuilder.createFormBuilder()

        listOfItems.each {
            f.addLabeledComponent(it.label, it.item, 1, false)
        }

        mainPanel = f
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel()
    }

    protected static class FormBuilderHolder {
        JBLabel label
        JComponent item
    }
}
