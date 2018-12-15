package chapter6;

/**
 *
 * @author: godder
 * @date: 2018/11/28
 */

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;


/**
 * 二叉树结点
 * @param <T>
 */
public class BinNode<T> implements Serializable {
    private static final String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
    private static final long serialVersionUID = 1L;
    private T value;
    private BinNode<T> left, right;

    public BinNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public BinNode<T> getLeft() {
        return left;
    }

    public BinNode<T> getRight() {
        return right;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setLeft(BinNode<T> left) {
        this.left = left;
    }

    public void setRight(BinNode<T> right) {
        this.right = right;
    }

    /**
     * 先序遍历
     * @param showNull
     * @param nullRepect  代表空值
     * @return
     */
    public List<T> preoderTraverse(boolean showNull, T nullRepect) {
        List<T> list = new LinkedList<T>();
        pretraverseOne(this.left, showNull, nullRepect, list);
        list.add(value);
        pretraverseOne(this.right, showNull, nullRepect, list);
        return list;
    }

    private void pretraverseOne(BinNode<T> node, boolean showNull, T nullRepect, List<T> list) {
        if (node == null) {
            if (showNull)
            list.add(nullRepect);
            return;
        }
        pretraverseOne(node.left, showNull, nullRepect, list);
        list.add(node.value);
        pretraverseOne(node.right, showNull, nullRepect, list);
    }

    /**
     * 中序遍历
     * @param nullRepect  代表空值
     * @return
     */
    public List<T> inoderTraverse(boolean showNull, T nullRepect) {
        List<T> list = new LinkedList<T>();
        list.add(value);
        intraverseOne(this.left, showNull, nullRepect, list);
        intraverseOne(this.right, showNull, nullRepect, list);
        return list;
    }

    private void intraverseOne(BinNode<T> node, boolean showNull, T nullRepect, List<T> list) {
        if (node == null) {
            if (showNull)
            list.add(nullRepect);
            return;
        }
        list.add(node.value);
        intraverseOne(node.left, showNull, nullRepect, list);
        intraverseOne(node.right, showNull, nullRepect, list);
    }

    /**
     * 后续遍历
     * @param nullRepect  代表空值
     * @return
     */
    public List<T> postorderTraverse(boolean showNull, T nullRepect) {
        List<T> list = new LinkedList<T>();
        postTraverseOne(this.right, showNull,nullRepect, list);
        list.add(value);
        postTraverseOne(this.left, showNull, nullRepect, list);
        return list;
    }

    private void postTraverseOne(BinNode<T> node, boolean showNull, T nullRepect, List<T> list) {
        if (node == null){
            if (showNull)
            list.add(nullRepect);
            return;
        }
        postTraverseOne(node.right, showNull, nullRepect, list);
        list.add(node.value);
        postTraverseOne(node.left, showNull, nullRepect, list);
    }

    /**
     * 先序获取所有孩子节点
     * @return
     */
    public List<BinNode<T>> getChildren() {
        List<BinNode<T>> list = new LinkedList<BinNode<T>>();
        Stack<BinNode<T>> stack = new Stack<BinNode<T>>();

        BinNode<T> current = this.getLeft();
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                list.add(current);
                stack.push(current);
                if (current.left != null) {
                    current = current.left;
                } else {
                    current = stack.pop().right;
                }
            } else {
                current = stack.pop().right;
            }
        }
        current = this.getRight();
        stack.clear();
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                list.add(current);
                stack.push(current);
                if (current.left != null) {
                    current = current.left;
                } else {
                    current = stack.pop().right;
                }
            } else {
                current = stack.pop().right;
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("value:");
        sb.append(this.value.toString());
        sb.append("\nleft:");
        sb.append(this.left.getValue().toString());
        sb.append("\nright:");
        sb.append(this.right.getValue().toString());
        return sb.toString();
    }
}
