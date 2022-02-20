package org.thepeacockproject.idea.structs;

import com.google.gson.annotations.SerializedName
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.thepeacockproject.idea.editor.internal.OptionalField
import org.thepeacockproject.idea.editor.internal.WithCheckBox

@CompileStatic
class GlacierContractData {
    @SerializedName("Objectives")
    @NotNull
    public GlacierContractObjective[] objectives

    @SerializedName("EnableSaving")
    @WithCheckBox("Enable saving: ")
    @OptionalField
    public Boolean enableSaves
}
