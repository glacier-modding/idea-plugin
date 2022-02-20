package org.thepeacockproject.idea.repo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thepeacockproject.idea.settings.PeacockSettingsState;

public class RepositoryManager {
    private static final Logger LOGGER = Logger.getInstance("PeacockRepositoryManager");
    private static final RepositoryManager INSTANCE = new RepositoryManager();

    @NotNull
    public static RepositoryManager getInstance() {
        return INSTANCE;
    }

    @Nullable
    public GameRepositoryEntry[] getEntries() {
        final PeacockSettingsState settingsState = PeacockSettingsState.getInstance();

        if (settingsState == null) {
            return null;
        }

        final Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        try {
            return gson.fromJson(settingsState.repository, GameRepositoryEntry[].class);
        } catch (Exception e) {
            LOGGER.error("Invalid repository provided!");
            LOGGER.error(e);

            return new GameRepositoryEntry[0];
        }
    }
}
