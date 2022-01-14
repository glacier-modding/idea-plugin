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
    public static @Nullable PsiElement rootStateMachine(@NotNull final PsiElement node) {
        return psiHoistNodes(node, 4);
    }

    /**
     * Get a PSI element that is up the tree by the selected number of nodes.
     *
     * @param node The current node.
     * @param depth How far to go up.
     * @return The node or null.
     */
    public static @Nullable PsiElement psiHoistNodes(@NotNull PsiElement node, final int depth) {
        for (int i = 0; i < depth; i++) {
            if (node != null) {
                node = node.getParent();
            }
        }

        if (node == null) {
            LOGGER.info("Got a null element while hoisting.");
            return null;
        }

        return node;
    }
}
