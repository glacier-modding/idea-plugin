package org.thepeacockproject.idea.intelligence;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;
import org.thepeacockproject.idea.intelligence.data.StateData;
import org.thepeacockproject.idea.intelligence.data.StateMachineData;
import org.thepeacockproject.idea.intelligence.visitors.PsiStateMachineVisitor;
import org.thepeacockproject.idea.services.GlacierProjectService;

import java.util.List;
import java.util.Objects;

import static org.thepeacockproject.idea.intelligence.visitors.JsonVisitorUtils.*;

public class JsonGotoDeclarationHandler implements GotoDeclarationHandler {
    private static final Logger LOGGER = Logger.getInstance("PeacockJsonGotoDeclarationHandler");

    @Override
    public PsiElement @Nullable [] getGotoDeclarationTargets(@Nullable final PsiElement sourceElement, final int offset, final Editor editor) {
        if (sourceElement == null) {
            LOGGER.info("Skipping navigation, source element is null.");
            return new PsiElement[0];
        }

        final PsiElement jsonKey = jsonKeyFromValue(sourceElement.getParent());

        if (jsonKey == null) {
            LOGGER.info("jsonKey is null, skipping.");

            return new PsiElement[0];
        }

        if (
                "JsonStringLiteralImpl".equals(className(jsonKey)) &&
                        jsonKey.textMatches("\"Transition\"")
        ) {
            LOGGER.info("Providing location.");

            // root state machine
            final PsiElement r = psiHoistNodes(jsonKey.getParent(), 9);

            final String stateMachineId = PsiStateMachineVisitor.getStateMachineId(r);

            final GlacierProjectService projectService = Objects
                    .requireNonNull(editor.getProject())
                    .getService(GlacierProjectService.class);

            // TODO: Cache this
            projectService.computeFor(sourceElement.getContainingFile());

            final List<StateMachineData> states = projectService.getDataFor(
                    sourceElement.getContainingFile().getName()
            );

            for (StateMachineData sm : states) {
                if (sm.id != null && Objects.requireNonNullElse(stateMachineId, "").equals(sm.id)) {
                    LOGGER.info("Found target with matching ID (" + sm.id + ").");

                    for (StateData state : sm.stateDataList) {
                        if (sourceElement.textMatches("\"" + state.name + "\"")) {
                            return new PsiElement[] { state.anchor.getElement() };
                        }
                    }

                    LOGGER.info("Target didn't have the state (" + sourceElement.getText() + ") inside it!");
                }
            }
        }

        return new PsiElement[0];
    }
}
