package org.thepeacockproject.idea.trees

import com.intellij.openapi.diagnostic.Logger
import com.intellij.ui.treeStructure.Tree
import groovy.json.JsonException
import groovy.json.JsonSlurper
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import java.util.concurrent.atomic.AtomicInteger

class GTreeCreator {
    private static final Logger LOGGER = Logger.getInstance("GlacierTreeCreator")

    @Nullable
    static Tree createRecursiveTreeFrom(@Nullable final String json) {
        if (json == null || json.isEmpty()) {
            return null
        }

        final JsonSlurper slurper = new JsonSlurper()

        def parsed

        try {
            parsed = slurper.parseText(json)
        } catch (JsonException e) {
            LOGGER.info("Parsing of JSON failed: ${e.getMessage()}")
            return null
        }

        final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu")

        recursiveAddNodes(parsed, root)

        final Tree t = new Tree(new DefaultTreeModel(root))

        return t
    }

    @NotNull
    static boolean isMenuNode(@Nullable final Map<String, ?> node) {
        if (node == null) {
            return false
        }

        return node.containsKey("children")
    }

    static void recursiveAddNodes(def children, DefaultMutableTreeNode parent) {
        final boolean isAMenuNode = isMenuNode(children)

        if (isAMenuNode) {
            DefaultMutableTreeNode t = new DefaultMutableTreeNode("${children.get("id")} (${children.get("view")})")

            parent.add(t)

            parent = t
        }

        children.keySet().forEach({ key ->
            final def value = children.get(key)
            final boolean isObjectLike = value instanceof Map
            final boolean isArrayLike = value instanceof List

            final String typeString = {
                if (isObjectLike) {
                    "object"
                } else if (isArrayLike) {
                    "array"
                } else {
                    "unknown"
                }
            }

            LOGGER.info("key: $key, value: $value, type: $typeString")

            if (isObjectLike) {
                final Map<String, ?> childrenMap = value as Map<String, ?>
                final DefaultMutableTreeNode node = new DefaultMutableTreeNode(key)
                parent.add(node)
                recursiveAddNodes(childrenMap, node)
            }

            if (isArrayLike) {
                final List<?> childrenArray = value as List<?>
                final AtomicInteger i = new AtomicInteger(0)

                final DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(key)

                parent.add(arrayNode)

                childrenArray.forEach {
                    final DefaultMutableTreeNode node = new DefaultMutableTreeNode("[${i.getAndIncrement()}]")

                    final boolean isChildObjectLike = it instanceof Map

                    arrayNode.add(node)

                    if (isChildObjectLike) {
                        recursiveAddNodes(it, node)
                    } else {
                        node.add(new DefaultMutableTreeNode(it))
                    }
                }
            }

            if (!isObjectLike && !isArrayLike) {
                parent.add(new DefaultMutableTreeNode("${key}: ${value}"))
            }
        })
    }
}
