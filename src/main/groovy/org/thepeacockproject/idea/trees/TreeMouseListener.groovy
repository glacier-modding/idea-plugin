package org.thepeacockproject.idea.trees

import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.JBMenuItem
import com.intellij.openapi.ui.JBPopupMenu
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull

import javax.swing.JTree
import javax.swing.tree.TreePath

//import javax.swing.tree.TreePath
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

@CompileStatic
class TreeMouseListener extends MouseAdapter {
    @NotNull
    private JTree tree

    @NotNull
    private GTreeCreator creator

    @Override
    void mousePressed(final MouseEvent e) {
        final int selRow = tree.getRowForLocation(e.getX(), e.getY())
        final TreePath selPath = tree.getPathForLocation(e.getX(), e.getY())

        if (selRow != -1 && e.getButton() == 3) {
            println selPath

            final JBPopupMenu menu = new JBPopupMenu("Options")

            final JBMenuItem menuItem = new JBMenuItem("Remove", AllIcons.General.Remove)
            menuItem.addActionListener {
                this.creator.onRemove(selPath, this.tree)
            }

            menu.add(menuItem, AllIcons.General.Remove)

            menu.show(this.tree, e.getX(), e.getY())
        }
    }
}
