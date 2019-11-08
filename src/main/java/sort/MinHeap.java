package sort;

import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: godder
 * @date: 2019/11/7
 */
@NoArgsConstructor
public class MinHeap<T extends Comparable<T>> {
    private BinaryTree<T> root = null;

    public void insert(T newElement) {
        if (this.root == null) {
            this.root = new BinaryTree<>(newElement);
            return;
        }
        // find the position to insert and change the balance of node in the path from root to leaf
        BinaryTree<T> currentNode = this.root, parentNode = null;
        while (currentNode != null && currentNode.getElement().compareTo(newElement) < 0) {
            if (currentNode.getBalance() >= 0) {
                currentNode.setBalance(currentNode.getBalance()-1);
                parentNode = currentNode;
                currentNode = currentNode.getLeft();
            } else {
                currentNode.setBalance(currentNode.getBalance()+1);
                parentNode = currentNode;
                currentNode = currentNode.getRight();
            }
        }
        // new position is leaf
        if (currentNode == null) {
            if (parentNode.getLeft() == null) {
                parentNode.setLeft(new BinaryTree<>(newElement));
            }
            else {
                parentNode.setRight(new BinaryTree<>(newElement));
            }
            return;
        }
        // replace original node by new node, and take original node as the new child of new node.
        this.insertNode(currentNode, newElement);
    }

    /**
     * add the node with new element at the position of current node. And swap child node recursively util add a new node.
     * @param node the position node of the node with new element insert.
     * @param newElement the element of inserted node
     * @param <T> the type of element, must be same as heap.
     */
    private <T extends Comparable<T>> void insertNode(BinaryTree<T> node, T newElement) {
        T temp = node.getElement();
        node.setElement(newElement);
        BinaryTree<T> currentNode = node.getBalance() >= 0? node.getLeft() : node.getRight();
        node.setBalance(node.getBalance() >= 0? node.getBalance()-1 : node.getBalance()+1);
        // swap node recursively util to leaf
        if (currentNode != null) {
            this.insertNode(currentNode, temp);
        }
        else {
            // add new node
            if (node.getLeft() == null) {
                node.setLeft(new BinaryTree<>(temp));
            } else {
                node.setRight(new BinaryTree<>(temp));
            }
        }
    }

    public T pop() {
        if (this.root == null) {
            return null;
        }
        T output = root.getElement();
        BinaryTree<T> currentNode = this.root, parentNode = currentNode;
        while (currentNode != null) {
            BinaryTree<T> nextNode = null;
            if (currentNode.getRight() == null || (currentNode.getLeft() != null && currentNode.getLeft().compareTo(currentNode.getRight()) <= 0)) {
                nextNode = currentNode.getLeft();
                currentNode.setBalance(currentNode.getBalance()+1);
            }
            else {
                nextNode = currentNode.getRight();
                currentNode.setBalance(currentNode.getBalance()-1);
            }
            T temp = currentNode.getElement();
            parentNode.setElement(temp);
            if (nextNode == null) {
                break;
            }
            parentNode = currentNode;
            currentNode = nextNode;

        }
        if (parentNode.getRight() == null || (parentNode.getLeft() != null && parentNode.getLeft().compareTo(parentNode.getRight()) <= 0)) {
            parentNode.setLeft(null);
        } else {
            parentNode.setRight(null);
        }
        return output;
    }

    @Override
    public String toString() {
        List<T> list = new LinkedList<>();
        this.root.inorderTraverse(list);
        StringBuilder stringBuilder = new StringBuilder();
        for (T element : list) {
            stringBuilder.append(element);
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }
}
