package sort;


import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author: godder
 * @date: 2019/11/7
 */
@Getter
@Setter
@AllArgsConstructor
public class BinaryTree<T extends Comparable<T>> implements Comparable<BinaryTree<T>> {
    private T element;
    private BinaryTree<T> left = null;
    private BinaryTree<T> right = null;
    private int balance = 0;

    public BinaryTree(@NotNull T element) {
        this.element = element;
    }

    public void preorderTraverse(List<T> list) {
        if (this.left != null) {
            this.left.preorderTraverse(list);
        }
        list.add(this.element);
        if (this.right != null) {
            this.right.preorderTraverse(list);
        }
    }

    public void inorderTraverse(List<T> list) {
        list.add(this.element);
        if (this.left != null) {
            this.left.inorderTraverse(list);
        }
        if (this.right != null) {
            this.right.inorderTraverse(list);
        }
    }

    public void postorderTraverse(List<T> list) {
        if (this.left != null) {
            this.left.postorderTraverse(list);
        }
        if (this.right != null) {
            this.right.postorderTraverse(list);
        }
        list.add(this.element);
    }


    @Override
    public int compareTo(BinaryTree<T> o) {
        return this.element.compareTo(o.getElement());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("element:");
        stringBuilder.append(this.element.toString());
        stringBuilder.append("\t balance:");
        stringBuilder.append(this.balance);
        return stringBuilder.toString();
    }
}

