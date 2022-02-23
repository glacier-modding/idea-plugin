package org.thepeacockproject.idea.structs

import com.google.gson.annotations.SerializedName
import groovy.transform.CompileStatic
import org.jetbrains.annotations.Nullable

@CompileStatic
class GlacierContractObjectiveDefinition {
    @SerializedName("Scope")
    public String scope

    @SerializedName("Context")
    public Map<String, ?> context

    @Nullable
    @SerializedName("Constants")
    public Map<String, ?> constants

    @SerializedName("States")
    public Map<String, ?> states
}
