package org.thepeacockproject.idea.intelligence.navigation;

import com.intellij.navigation.DirectNavigationProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class JsonDirectNavigationProvider implements DirectNavigationProvider {
    @Override
    public @Nullable PsiElement getNavigationElement(@NotNull PsiElement element) {
        return null;
    }
}
