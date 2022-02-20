package org.thepeacockproject.idea.structs

import com.google.gson.annotations.SerializedName
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.thepeacockproject.idea.editor.internal.WithTextInput

@CompileStatic
class GlacierContractMetadata {
    @SerializedName("Id")
    @NotNull
    @WithTextInput("Contract ID: ")
    public String id

    @SerializedName("Type")
    @NotNull
    @WithTextInput("Contract type: ")
    public String type

    @SerializedName("Title")
    @NotNull
    @WithTextInput("Contract name: ")
    public String title

    @SerializedName("Description")
    @Nullable
    @WithTextInput("Contract description: ")
    public String description

    @SerializedName("TileImage")
    @Nullable
    @WithTextInput("Contract tile image: ")
    public String tileImage
}
