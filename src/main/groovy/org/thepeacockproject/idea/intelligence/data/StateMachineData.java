package org.thepeacockproject.idea.intelligence.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StateMachineData {
    public @Nullable String id;
    public @NotNull List<StateData> stateDataList;

    public StateMachineData() {
        this.id = null;
        this.stateDataList = new ArrayList<>();
    }
}
