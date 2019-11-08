package chapter6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class BinSearchTreeTest {
    BinSearchTree<Integer> binSearchTree;
    List<Integer> list;
    Random random;

    @Before
    public void setUp() throws Exception {
        list = TestUtils.getRandomList(20);
        binSearchTree = new BinSearchTree<>(list);
        random = new Random(System.currentTimeMillis());
    }

    @Test
    public void insert() {
        int r = random.nextInt();

        System.out.println(binSearchTree.insert(r));
        list.add(r);
        getSortedList();
    }

    @Test
    public void search() {
        for (Integer i : list) {
            assert binSearchTree.search(i);
        }
        int r = random.nextInt();
        while (list.contains(r)) {
            r = random.nextInt();
        }
        assert !binSearchTree.search(r);
    }

    @Test
    public void remove() {
        int r = random.nextInt();
        while (list.contains(r)) {
            r = random.nextInt();
        }
        assert !binSearchTree.remove(r);
        r = random.nextInt(list.size());
        Integer integer = list.get(r);
        list.remove(r);
        assert binSearchTree.remove(integer);

    }

    @Test
    public void getNodeNum() {
        assert list.size() == binSearchTree.getNodeNum();
    }

    @Test
    public void getMax() {
        int max = list.get(0);
        for (Integer i : list) {
            if (max < i) {
                max = i;
            }
        }
        assert max == binSearchTree.getMax();
    }

    @Test
    public void getMin() {
        int min = list.get(0);
        for (Integer i : list) {
            if (i < min) {
                min = i;
            }
        }
        assert min == binSearchTree.getMin();
    }

    @Test
    public void getSortedList() {
        List<Integer> heapList = binSearchTree.getSortedList();
        Integer heapArray[] = new Integer[heapList.size()];
        heapList.toArray(heapArray);
        System.out.println(heapList);
        Integer array[] = new Integer[list.size()];
        list.toArray(array);
        Arrays.sort(array);
        Assert.assertArrayEquals(array, heapArray);
    }
}