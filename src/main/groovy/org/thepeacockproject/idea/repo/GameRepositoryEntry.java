package org.thepeacockproject.idea.repo;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameRepositoryEntry {
    @SerializedName("ID_")
    @NotNull
    public String id;

    @SerializedName("Name")
    @Nullable
    public String name;
}
