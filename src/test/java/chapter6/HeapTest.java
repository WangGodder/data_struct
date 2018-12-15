package chapter6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class HeapTest {
    Heap<Integer> heap;
    List<Integer> list;
    Random random;

    @Before
    public void setUp() throws Exception {
        list = TestUtils.getRandomList(8);
        heap = new Heap<>(list);
        random = new Random(System.currentTimeMillis());
    }

    @Test
    public void insert() {
        int r = random.nextInt();

        System.out.println(heap.insert(r));
        list.add(r);
        getSortedList();
    }

    @Test
    public void search() {
        for (Integer i : list) {
            assert heap.search(i);
        }
        int r = random.nextInt();
        while (list.contains(r)) {
            r = random.nextInt();
        }
        assert !heap.search(r);
    }

    @Test
    public void remove() {
        int r = random.nextInt();
        while (list.contains(r)) {
            r = random.nextInt();
        }
        assert !heap.remove(r);
        r = random.nextInt(list.size());
        Integer integer = list.get(r);
        list.remove(r);
        assert heap.remove(integer);

    }

    @Test
    public void getNodeNum() {
        assert list.size() == heap.getNodeNum();
    }

    @Test
    public void getMax() {
        int max = list.get(0);
        for (Integer i : list) {
            if (max < i) {
                max = i;
            }
        }
        assert max == heap.getMax();
    }

    @Test
    public void getMin() {
        int min = list.get(0);
        for (Integer i : list) {
            if (i < min) {
                min = i;
            }
        }
        assert min == heap.getMin();
    }

    @Test
    public void getSortedList() {
        List<Integer> heapList = heap.getSortedList();
        Integer heapArray[] = new Integer[heapList.size()];
        heapList.toArray(heapArray);
        System.out.println(heapList);
        Integer array[] = new Integer[list.size()];
        list.toArray(array);
        Arrays.sort(array);
        Assert.assertArrayEquals(array, heapArray);
    }
}