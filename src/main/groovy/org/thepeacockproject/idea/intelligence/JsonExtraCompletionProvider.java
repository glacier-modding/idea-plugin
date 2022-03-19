package org.thepeacockproject.idea.intelligence;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thepeacockproject.idea.intelligence.data.StateData;
import org.thepeacockproject.idea.intelligence.data.StateMachineData;
import org.thepeacockproject.idea.services.GlacierProjectService;

import java.util.List;
import java.util.Objects;

import static org.thepeacockproject.idea.intelligence.visitors.JsonVisitorUtils.*;

public class JsonExtraCompletionProvider extends CompletionContributor {
    private static final Logger LOGGER = Logger.getInstance("GlacierJsonCompletionProvider");

    @Override
    public void fillCompletionVariants(
            @NotNull final CompletionParameters parameters,
            @NotNull final CompletionResultSet result
    ) {
        final PsiElement jsonValue = parameters.getPosition().getParent();
        final PsiElement jsonKey = jsonKeyFromValue(jsonValue);

        if (jsonKey == null) {
            LOGGER.debug("jsonKey is null, skipping.");

            super.fillCompletionVariants(parameters, result);
            return;
        }

        if (
                "JsonStringLiteralImpl".equals(className(jsonKey)) &&
                        jsonKey.textMatches("\"Transition\"")
        ) {
            LOGGER.info("Providing completions!");

            final GlacierProjectService projectService = Objects
                    .requireNonNull(parameters.getEditor().getProject())
                    .getService(GlacierProjectService.class);

            // TODO: Cache this
            projectService.computeFor(parameters.getOriginalFile());

            final List<StateMachineData> states = projectService.getDataFor(
                    parameters.getOriginalFile().getName()
            );

            for (StateMachineData sm : states) {
                for (StateData state : sm.stateDataList) {
                    result.addElement(new LookupElement() {
                        @Override
                        public @NotNull String getLookupString() {
                            return state.name;
                        }

                        @Override
                        public @Nullable PsiElement getPsiElement() {
                            return state.anchor.getElement();
                        }
                    });
                }
            }
        }

        super.fillCompletionVariants(parameters, result);
    }
}
