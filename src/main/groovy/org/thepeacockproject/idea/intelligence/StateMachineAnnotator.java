package org.thepeacockproject.idea.intelligence;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class StateMachineAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull final AnnotationHolder holder) {
        if (
                element.textMatches("\"Transition\"")
                || element.textMatches("\"States\"")
                || element.textMatches("\"Condition\"")
                || element.textMatches("\"Actions\"")
        ) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .textAttributes(DefaultLanguageHighlighterColors.NUMBER)
                    .create();
        }
    }
}
