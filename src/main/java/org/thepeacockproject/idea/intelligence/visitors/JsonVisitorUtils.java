package org.thepeacockproject.idea.intelligence.visitors;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JsonVisitorUtils {
    private static final Logger LOGGER = Logger.getInstance("JsonVisitorUtils");

    public static @NotNull String className(@NotNull final PsiElement e) {
        return e.getClass().getSimpleName();
    }

    public static @Nullable PsiElement jsonKeyFromValue(@NotNull PsiElement value) {
        value = value.getPrevSibling();

        if (value == null) {
            LOGGER.info("Failed to find JSON key from value: previous sibling was null.");
            return null;
        }

        while (!"JsonStringLiteralImpl".equals(className(value))) {
            value = value.getPrevSibling();

            if (value == null) {
                return null;
            }
        }

        return value;
    }

    /** This function assumes we are 5 nodes deep inside a state machine. */
    public static @Nullable PsiElement rootStateMachine(@NotNull PsiElement maybeGggp) {
        // gggp = great great grandparent

        // we'll try going up 5 times only
        for (int i = 0; i < 4; i++) {
            if (maybeGggp != null) {
                maybeGggp = maybeGggp.getParent();
            }
        }

        if (maybeGggp == null) {
            LOGGER.info("Wasn't able to find a grandparent :(");
            return null;
        }

        return maybeGggp;
    }
}
