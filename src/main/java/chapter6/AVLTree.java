package chapter6;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author: godder
 * @date: 2018/12/26
 */
public class AVLTree<T extends Comparable<T>> extends BinSearchTree<T> {
    private static final String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    private static final long serialVersionUID = 1L;
    protected int minHeight = 0;
    protected int maxHeight = 0;

    public AVLTree() {
        super();
    }

    public AVLTree(BinNode<T> root) {
        List<BinNode<T>> list = root.getChildren();
        list.add(root);
        for (BinNode<T> node : list) {
            insert(node.getValue());
        }
        this.nodeNum = list.size();
    }

    public  AVLTree(List<T> list) {
        for (T e : list) {
            insert(e);
        }
        this.nodeNum = list.size();
    }

    @Override
    public int insert(T element) {
        BinNode<T> parentNode = null, gradParentNode = null;
        int compareTimes =  super.insert(element, parentNode, gradParentNode);
        // rotate this tree, after insert new element, the new tree height is compare times + 1
        if (compareTimes - this.minHeight > 0) {

            this.minHeight ++;
        }
        return 0;
    }

    private void rotateLeft(BinNode<T> node, BinNode<T> parentNode) {
        BinNode<T> newRoot = node.getRight(), newNodeRight = newRoot.getLeft();
        node.setRight(newNodeRight);
        if (parentNode == null && this.root.equals(node)) {
            this.root = newRoot;
        } else {
            if (parentNode.getLeft().equals(node)) {
                parentNode.setLeft(newRoot);
            } else {
                parentNode.setRight(newRoot);
            }
        }
        newRoot.setLeft(node);
    }

    private void rotateRight(BinNode<T> node, BinNode<T> parentNode) {
        BinNode<T> newRoot = node.getLeft(), newNodeLeft = newRoot.getRight();
        node.setLeft(newNodeLeft);
        if (parentNode == null && this.root.equals(node)) {
            this.root = newRoot;
        } else {
            if (parentNode.getLeft().equals(node)) {
                parentNode.setLeft(newRoot);
            } else {
                parentNode.setRight(newRoot);
            }
        }
        newRoot.setRight(node);
    }

    @Override
    public boolean remove(T element) {
        return super.remove(element);
    }
}
