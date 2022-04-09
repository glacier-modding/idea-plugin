package org.thepeacockproject.idea.trees

import com.intellij.openapi.diagnostic.Logger
import com.intellij.ui.treeStructure.Tree
import groovy.json.JsonException
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import java.util.concurrent.atomic.AtomicInteger

@CompileStatic
class GTreeCreator {
    private static final Logger LOGGER = Logger.getInstance("GlacierTreeCreator")

    @Nullable
    def parsed = null

    GTreeCreator(@Nullable final String json) {
        if (json == null || json.isEmpty()) {
            return
        }

        final JsonSlurper slurper = new JsonSlurper()

        try {
            this.parsed = slurper.parseText(json)
        } catch (JsonException e) {
            LOGGER.info("Parsing of JSON failed: ${e.getMessage()}")
        }
    }

    @Nullable
    Tree createRecursiveTree() {
        if (this.parsed == null) {
            return null
        }

        final TreeNodeWithData root = new TreeNodeWithData("Menu", "_ROOTOBJECT")

        recursiveAddNodes(parsed, root)

        final Tree t = new Tree(new DefaultTreeModel(root))

        final TreeMouseListener ml = new TreeMouseListener(tree: t)

        t.addMouseListener(ml)

        return t
    }

    @NotNull
    static boolean isMenuNode(@Nullable final def node) {
        if (node == null) {
            return false
        }

        return node instanceof Map && (node as Map).containsKey("children")
    }

    /**
     * Delegates iteration of the next node to {@link GTreeCreator#itemsIteratorClosure}.
     * Note: no nodes are created in this function. All of that is handled elsewhere, this
     * just handles object-like and array-like things.
     *
     * @param children The parent's child nodes.
     * @param parent The parent node.
     */
    static void recursiveAddNodes(@NotNull final def children, @NotNull TreeNodeWithData parent) {
        final boolean isAMenuNode = isMenuNode(children)

        if (isAMenuNode) {
            final Map<String, ?> node = (Map<String, ?>) children
            final TreeNodeWithData t = new TreeNodeWithData("${node.id} (${node.view})", parent.peacockPath)

            parent.realChild = t

            parent.add(t)

            parent = t
        }

        if (children instanceof Map) {
            children.keySet().forEach { key ->
                final def value = children[key as String]

                itemsIteratorClosure(key as String, value, parent)
            }
        }

        if (children instanceof List) {
            for (int i = 0; i < children.size(); i++) {
                final def value = (children as List).get(i)

                itemsIteratorClosure("[$i]" as String, value, parent)
            }
        }
    }

    /**
     * Iterates over every individual node, deciding what to do with it.
     * @param key The node's key.
     * @param value The node's value.
     * @param parent The node's parent.
     */
    static void itemsIteratorClosure(@NotNull String key, @NotNull def value, @NotNull TreeNodeWithData parent) {
        final boolean isObjectLike = value instanceof Map
        final boolean isArrayLike = value instanceof List

        final String typeString = isObjectLike ? "object" : (isArrayLike ? "array" : "unknown")

        LOGGER.info("key: $key, value: $value, type: $typeString")

        if (isObjectLike) {
            final Map<String, ?> childrenMap = value as Map<String, ?>
            final TreeNodeWithData node = new TreeNodeWithData(key, parent.peacockPath + ".$key")
            parent.add(node)
            recursiveAddNodes(childrenMap, node)
            return
        }

        if (isArrayLike) {
            final List<?> childrenArray = value as List<?>
            final AtomicInteger i = new AtomicInteger(0)
            final DefaultMutableTreeNode arrayNode = new TreeNodeWithData(key, parent.peacockPath)

            parent.add(arrayNode)

            childrenArray.forEach {
                final int index = i.getAndIncrement()
                final TreeNodeWithData node = new TreeNodeWithData("[$index]", parent.peacockPath + "[$index]")

                arrayNode.add(node)

                recursiveAddNodes(it, node)
            }
            return
        }

        if (!isObjectLike && !isArrayLike) {
            parent.add(new TreeNodeWithData("${key}: ${value}", parent.peacockPath + ".$key"))
        }
    }

    protected static final class TreeNodeWithData extends DefaultMutableTreeNode {
        @NotNull
        final String peacockPath
        @Nullable
        TreeNodeWithData realChild = null

        TreeNodeWithData(@NotNull final def text, @NotNull final String peacockPath) {
            super(text)
            this.peacockPath = peacockPath
        }
    }
}
