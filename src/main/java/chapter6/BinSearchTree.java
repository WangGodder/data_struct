package chapter6;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author: godder
 * @date: 2018/11/28
 */
public class BinSearchTree<T extends Comparable<T>> implements Serializable {
    private static final String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    private static final long serialVersionUID = 1L;
    protected BinNode<T> root;
    protected int nodeNum = 0;

    public BinSearchTree() {}

    public BinSearchTree(BinNode<T> root) {
        List<BinNode<T>> list = root.getChildren();
        list.add(root);
        for (BinNode<T> node : list) {
            insert(node.getValue());
        }
        this.nodeNum = list.size();
    }

    public BinSearchTree(List<T> list) {
        for (T e : list) {
            insert(e);
        }
        this.nodeNum = list.size();
    }

    /**
     * insert a element into heap return the times to compare
     * @param element
     * @return compare times
     */
    public int insert(T element) {
        return insert(element, null, null);
    }

    public int insert(T element, BinNode<T> newNodeParent, BinNode<T> newNodeGradParent) {
        if (root == null) {
            this.root = new BinNode<T>(element);
            return 0;
        }
        BinNode<T> current  = this.root, parentCurrent = null, gradParentCurrent = null;
        // 添加子节点的方向， 0表示相等情况，1右子节点，-1左子节点
        int child = 0, times = 0;

        // location
        while (current != null) {
            times++;
            gradParentCurrent = parentCurrent;
            parentCurrent = current;
            child = element.compareTo(current.getValue());
            if (child == 0) {
                break;
            }
            if (child < 0) {
                current = current.getLeft();
            }
            if (child > 0) {
                current = current.getRight();
            }
        }
        // add node
        if (child == 0) {
            // if insert element equal to one element in this tree, then add new node to current's left child.
            // if current node has left child then make current node's left child to be new node's left child.
            if (current.getLeft() != null) {
                BinNode<T> currentLeft = current.getLeft();
                BinNode<T> newNode = new BinNode<T>(element);
                newNode.setLeft(currentLeft);
                current.setLeft(newNode);
                gradParentCurrent = parentCurrent;
                parentCurrent = current;
            } else {
                current.setLeft(new BinNode<T>(element));
            }
        }
        if (child > 0) {
            parentCurrent.setRight(new BinNode<T>(element));
        }
        if (child < 0) {
            parentCurrent.setLeft(new BinNode<T>(element));
        }

        // 将可能需要转置的节点输出
        newNodeParent = parentCurrent;
        newNodeGradParent = gradParentCurrent;

        return times;
    }

    /**
     * search an element whether in this tree
     * @param element
     * @return whether this element in this tree
     */
    public boolean search(T element) {
        BinNode<T> current = root;
        if (root == null) {
            return false;
        }
        int compare;
        while (current != null) {
            compare = element.compareTo(current.getValue());
            if (compare == 0) {
                return true;
            }
            if (compare > 0) {
                current = current.getRight();
            }
            if (compare < 0) {
                current = current.getLeft();
            }
        }
        return false;
    }

    /**
     * delete a element from heap
     * @param element
     * @return  whether success
     */
    public boolean remove(T element) {
        if (root == null) {
            return false;
        }
        // 获取删除节点
        BinNode<T> elementNode = root, parentNode = null;
        int lastcompare = 0;
        while (elementNode != null) {
            int compare = element.compareTo(elementNode.getValue());
            if (compare == 0) {
                break;
            }
            else {
                parentNode = elementNode;
                lastcompare = compare;
            }
            if (compare > 0) {
                elementNode = elementNode.getRight();
            }
            if (compare < 0) {
                elementNode = elementNode.getLeft();
            }
        }
        if (elementNode == null) {
            return false;
        }
        BinNode<T> replaceLeft = elementNode.getLeft(), replaceRight = elementNode.getRight();
        // 将删除节点的右子树的最小值作为代替节点，否则用左子树的最大值
        BinNode<T> replace = elementNode.getRight(), replaceParent = elementNode;
        if (replace == null) {
            replace = elementNode.getLeft();
            if (replace == null) {  // 没有可替换节点(删除叶子节点)
                return false;
            }
            if (replace.getRight() == null) {
                replaceLeft = replace.getLeft();
            }
            while (replace.getRight() != null) {
                replaceParent = replace;
                replace = replace.getRight();
            }
            replaceParent.setLeft(null);
        } else {
            if (replace.getLeft() == null) {
                replaceRight = replace.getRight();
            }
            while (replace.getLeft() != null) {
                replaceParent = replace;
                replace = replace.getLeft();
            }
            replaceParent.setRight(null);
        }
        replace.setLeft(replaceLeft);
        replace.setRight(replaceRight);
        if (parentNode == null) {
            root = replace;
        }
        if (lastcompare < 0) {
            parentNode.setLeft(replace);
        } else if (lastcompare > 0) {
            parentNode.setRight(replace);
        }
        return true;
    }

    public BinNode<T> getRoot() {
        return root;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    /**
     * get the max element in this tree
     * @return
     */
    public T getMax() {
        if (root == null) {
            return null;
        }
        BinNode<T> current = root;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getValue();
    }

    /**
     * get the min element in this tree
     * @return
     */
    public T getMin() {
        if (root == null) {
            return null;
        }
        BinNode<T> current = root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getValue();
    }

    /**
     * get a sorted list include all element in this tree
     * @return an list
     */
    public List<T> getSortedList() {
        return root.preoderTraverse(false,null);
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public static BinNode getMaxNode(BinSearchTree binSearchTree) {
        BinNode current = binSearchTree.getRoot();
        if (current == null) {
            return null;
        }
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }
}
