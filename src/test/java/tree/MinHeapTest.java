package tree;

import org.junit.Test;

/**
 * @author: godder
 * @date: 2019/11/7
 */
public class MinHeapTest {

    @Test
    public void insert() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(3);
        heap.insert(4);
        heap.insert(1);
        heap.insert(2);
        heap.insert(6);
        System.out.println(heap);
    }

    @Test
    public void pop() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(3);
        heap.insert(4);
        heap.insert(1);
        heap.insert(2);
        heap.insert(6);
        assert heap.pop() == 1;
        assert heap.pop() == 2;
        assert heap.pop() == 3;
        assert heap.pop() == 4;
        assert heap.pop() == 6;
    }
}