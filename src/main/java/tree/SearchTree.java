package tree;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author: godder
 * @date: 2019/11/11
 */
@Getter
@NoArgsConstructor
public class SearchTree<T extends Comparable<T>> {
    BinaryTree<T> root = null;

    /**
     * insert a new element into search tree.
     * @param element new element inserted
     */
    public void insert(T element) {
        if (this.root == null) {
            this.root = new BinaryTree<>(element);
            return;
        }
        BinaryTree<T> current = this.root, parent = null;
        while (current != null) {
            parent = current;
            if (element.compareTo(current.getElement()) == 0) {
                current = parent.getLeft();
                parent.setLeft(new BinaryTree<>(element));
                parent.getLeft().setLeft(current);
                return;
            }
            current = element.compareTo(current.getElement()) > 0 ? current.getRight() : current.getLeft();
        }
        if (element.compareTo(parent.getElement()) > 0) {
            parent.setRight(new BinaryTree<>(element));
        } else {
            parent.setLeft(new BinaryTree<>(element));
        }
    }

    public boolean delete(T element) {
        if (this.root == null) {
            return false;
        }
        BinaryTree<T> current = this.root, parent = null;
        while (current != null) {
            if (current.getElement().equals(element)) {
                break;
            }
            parent = current;
            current = element.compareTo(current.getElement()) > 0? current.getRight() : current.getLeft();
        }
        if (current == null) {
            return false;
        }
        while (current != null) {
            current = current.getLeft();
            if (current == null) {
                break;
            }
            parent.setElement(current.getElement());
            parent = current;
        }
        if (parent.getElement().compareTo(element) >= 0) {
            parent.setLeft(null);
        } else {
            parent.setRight(null);
        }
        return true;
    }

    public boolean find(T element) {
        if (this.root == null) {
            return false;
        }
        BinaryTree<T> current = this.root;
        int searchTimes = 0;
        while (current != null) {
            searchTimes++;
            if (current.getElement().equals(element)) {
                break;
            }
            current = element.compareTo(current.getElement()) > 0? current.getRight() : current.getLeft();
        }
        System.out.println(searchTimes);
        if (current == null) {
            return false;
        }
        return true;
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
