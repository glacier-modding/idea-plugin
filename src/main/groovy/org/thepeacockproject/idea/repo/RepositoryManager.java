package org.thepeacockproject.idea.repo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thepeacockproject.idea.settings.GlacierSettingsState;

public class RepositoryManager {
    private static final Logger LOGGER = Logger.getInstance("GlacierRepositoryManager");
    private static final RepositoryManager INSTANCE = new RepositoryManager();

    @NotNull
    public static RepositoryManager getInstance() {
        return INSTANCE;
    }

    @Nullable
    public GameRepositoryEntry[] getEntries() {
        final GlacierSettingsState settingsState = GlacierSettingsState.getInstance();

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
