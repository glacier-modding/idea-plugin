package org.thepeacockproject.idea.intelligence.data;

import com.intellij.psi.PsiElement;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.annotations.NotNull;

public class StateData {
    public @NotNull String name;
    public SmartPsiElementPointer<PsiElement> anchor;

    public StateData(
            final @NotNull String name,
            final @NotNull SmartPsiElementPointer<PsiElement> anchor
    ) {
        this.name = name;
        this.anchor = anchor;
    }

    public static @NotNull SmartPsiElementPointer<PsiElement> getPointerFor(final @NotNull PsiElement element) {
        return SmartPointerManager.getInstance(element.getProject()).createSmartPsiElementPointer(element);
    }
}
