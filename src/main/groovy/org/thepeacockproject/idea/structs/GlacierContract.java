package org.thepeacockproject.idea.structs;

import com.google.gson.annotations.SerializedName;
import groovy.transform.CompileStatic;
import org.jetbrains.annotations.NotNull;

@CompileStatic
public class GlacierContract {
    @SerializedName("Metadata")
    @NotNull
    public GlacierContractMetadata metadata;

    @SerializedName("Data")
    @NotNull
    public GlacierContractData data;
}
