package chapter7;

/**
 * @author: godder
 * @date: 2018/12/12
 */
public class AdjacencyNode<E> {
    int index = -1;
    E weight = null;
    AdjacencyNode<E> next = null;

    public AdjacencyNode(int index, E weight, AdjacencyNode<E> next) {
        this.index = index;
        this.weight = weight;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" | ");
        sb.append(this.index);
        sb.append(" | ");
        sb.append(this.weight.toString());
        sb.append(" | ");
        if (this.next != null) {
            sb.append("--->");
            sb.append(this.next.toString());
        }

        return sb.toString();
    }
}
