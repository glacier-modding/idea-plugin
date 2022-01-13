package org.thepeacockproject.idea.intelligence.visitors;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.thepeacockproject.idea.intelligence.data.StateData;
import org.thepeacockproject.idea.intelligence.data.StateMachineData;

import java.util.ArrayList;
import java.util.List;

import static org.thepeacockproject.idea.intelligence.visitors.JsonVisitorUtils.className;
import static org.thepeacockproject.idea.intelligence.visitors.JsonVisitorUtils.rootStateMachine;

public class PsiStateMachineVisitor extends PsiRecursiveElementWalkingVisitor {
    public List<StateMachineData> stateMachines;
    private static final Logger LOGGER = Logger.getInstance("PsiStateMachineVisitor");

    public PsiStateMachineVisitor() {
        super();
        this.stateMachines = new ArrayList<>();
    }

    @Override
    public void visitElement(@NotNull PsiElement element) {
        final String currentClass = className(element);

        if (currentClass.equals("JsonStringLiteralImpl")) {
            if (element.textMatches("\"States\"")) {
                PsiElement body = element.getNextSibling();

                while (!"JsonObjectImpl".equals(className(body))) {
                    body = body.getNextSibling();

                    if (body == null) {
                        super.visitElement(element);
                        return;
                    }
                }

                // we got the body
                for (PsiElement child : body.getChildren()) {
                    if (child.getChildren().length > 0) {
                        for (PsiElement grandChild : child.getChildren()) {
                            if (className(grandChild).equals("JsonStringLiteralImpl")) {
                                final StateData d = new StateData(grandChild.getText().replace("\"", ""), grandChild);

                                final StateMachineData sm = new StateMachineData();

                                sm.stateDataList.add(d);

                                sm.id = getStateMachineId(element);

                                if (sm.id == null) {
                                    break;
                                }

                                this.stateMachines.add(sm);
                            }
                        }
                    }
                }
            }
        }

        super.visitElement(element);
    }

    public static @Nullable String getStateMachineId(@NotNull PsiElement element) {
        // alright, we got a state, now let's figure out which objective this was in
        final PsiElement smCandidate = rootStateMachine(element);

        if (smCandidate == null) {
            LOGGER.error(new AssertionError("Candidate was null, not sure why."));
            return null;
        }

        for (PsiElement smNodeChild : smCandidate.getChildren()) {
            if (className(smNodeChild).equals("JsonPropertyImpl")) {
                for (PsiElement stateMachineChild : smNodeChild.getChildren()) {
                    if (className(stateMachineChild).equals("JsonStringLiteralImpl") && stateMachineChild.textMatches("\"Id\"")) {
                        // we got the Id field, let's get the value

                        final PsiElement idLiteral = stateMachineChild.getNextSibling().getNextSibling().getNextSibling();

                        return idLiteral.getText().replace("\"", "");
                    }
                }
            }
        }

        return null;
    }
}
