package chapter6;

import java.util.ArrayList;
import java.util.List;

public class MyBinTree<T> {
    private Node<T> root;

    public MyBinTree() {
        root = null;
    }

    public MyBinTree(Node<T> root) {
        this.root = root;
    }

    public MyBinTree(T[] elements) {


    }

    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorderNode(root, list);
        return list;
    }

    public List<T> midorderNode() {
        List<T> list = new ArrayList<T>();
        midorderNode(root, list);
        return list;
    }

    public List<T> postorderNode() {
        List<T> list = new ArrayList<T>();
        postorderNode(root, list);
        return list;
    }

    private void preorderNode(Node<T> node, List<T> list) {
        if (node == null ) {
            return;
        }
        preorderNode(node.left, list);
        list.add(node.value);
        preorderNode(node.right, list);
    }

    private void midorderNode(Node<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.value);
        midorderNode(node.left, list);
        midorderNode(node.right, list);
    }

    private void postorderNode(Node<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        postorderNode(node.left, list);
        postorderNode(node.right, list);
        list.add(node.value);
    }

    public Node<T> getRoot() {
        return this.root;
    }

    public static class Node<T> {
        public T value;
        public Node<T> left, right;

        public Node(T value) {
            this.value = value;
        }
    }
}
