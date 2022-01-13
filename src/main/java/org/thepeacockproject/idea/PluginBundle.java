package org.thepeacockproject.idea;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class PluginBundle extends DynamicBundle {
    @NonNls
    public static final String BUNDLE = "messages.PluginBundle";

    public PluginBundle() {
        super(BUNDLE);
    }

    @Contract(pure = true)
    public static @NotNull String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object ...args) {
        return INSTANCE.getMessage(key, args);
    }
}
