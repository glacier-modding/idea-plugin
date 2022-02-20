package org.thepeacockproject.idea.structs

import com.google.gson.annotations.SerializedName
import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@CompileStatic
@Builder(builderStrategy = SimpleStrategy)
class GlacierContractObjective {
    @SerializedName("Id")
    @NotNull
    public String id

    @SerializedName("Type")
    @NotNull
    public String type

    @SerializedName("Category")
    @NotNull
    public String category

    @SerializedName("Scope")
    @NotNull
    public String scope

    @SerializedName("Title")
    @Nullable
    public String title

    @SerializedName("Image")
    @Nullable
    public String image

    @SerializedName("BriefingName")
    @Nullable
    public String name

    @SerializedName("BriefingText")
    @Nullable
    public String description

    @SerializedName("LongBriefingText")
    @Nullable
    public String longDescription

    @SerializedName("ExcludeFromScoring")
    @Nullable
    public boolean excludeFromScoring
}
