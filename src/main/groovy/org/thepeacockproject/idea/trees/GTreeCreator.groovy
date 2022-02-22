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
    static boolean isMenuNode(@Nullable final def node) {
        if (node == null) {
            return false
        }

        return node instanceof Map && (node as Map).containsKey("children")
    }

    static void recursiveAddNodes(def children, DefaultMutableTreeNode parent) {
        final boolean isAMenuNode = isMenuNode(children)

        if (isAMenuNode) {
            Map<String, ?> node = (Map<String, ?>) children
            DefaultMutableTreeNode t = new DefaultMutableTreeNode("${node.id} (${node.view})")

            parent.add(t)

            parent = t
        }

        if (children instanceof Map) {
            children.keySet().forEach({ key ->
                def value = children[key as String]

                itemsIteratorClosure(key as String, value, parent)
            })
        }

        if (children instanceof List) {
            for (int i = 0; i < children.size(); i++) {
                def value = (children as List).get(i)

                itemsIteratorClosure("[$i]" as String, value, parent)
            }
        }
    }

    static void itemsIteratorClosure(String key, def value, DefaultMutableTreeNode parent) {
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
    }
}
