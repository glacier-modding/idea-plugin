package org.thepeacockproject.idea.services;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.thepeacockproject.idea.intelligence.data.StateMachineData;
import org.thepeacockproject.idea.intelligence.visitors.PsiStateMachineVisitor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlacierProjectService {
    protected static final Logger LOGGER = Logger.getInstance("GlacierProjectService");
    public final Map<String, List<StateMachineData>> jsonData;

    public GlacierProjectService(final @NotNull Project project) {
        LOGGER.info("Project service: " + project.getName());

        this.jsonData = new ConcurrentHashMap<>();
    }

    public List<StateMachineData> getDataFor(final String filename) {
        if (!this.jsonData.containsKey(filename)) {
            return null;
        }

        return this.jsonData.get(filename);
    }

    public void computeFor(final @NotNull PsiFile file) {
        final String fileName = file.getName();
        LOGGER.info("Performing computations for " + fileName);

        final PsiStateMachineVisitor w = new PsiStateMachineVisitor();

        w.visitFile(file);

        this.jsonData.put(fileName, w.stateMachines);
    }
}
