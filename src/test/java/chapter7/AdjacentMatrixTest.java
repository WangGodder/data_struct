package chapter7;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.*;
import static chapter7.TestUtils.*;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class AdjacentMatrixTest {
    private AdjacentMatrix<Integer, String> adjacentMatrix;
    private Random random;

    @Before
    public void setUp() throws Exception {
        adjacentMatrix = generatreAdjacencyMatrixG(10);
        System.out.println(adjacentMatrix);
        random = new Random(System.currentTimeMillis());
    }

    @Test
    public void toAdjacencyList() {
        System.out.println(adjacentMatrix.toAdjacencyList());
    }

    @Test
    public void deepthFirstSearch() {
        System.out.println(adjacentMatrix.deepthFirstSearch(random.nextInt(10)));
    }

    @Test
    public void breadthFirstSearch() {
        System.out.println(adjacentMatrix.breadthFirstSearch(random.nextInt(10)));
    }

    @Test
    public void getMinimumWay() {
        int a = random.nextInt(10), b = random.nextInt(10);
        while (a == b) {
            a = random.nextInt(10);
            b = random.nextInt(10);
        }
        toAdjacencyList();
        System.out.println(a + "\t" + b);
        System.out.println(adjacentMatrix.getMinimumWay(a, b, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }));
    }

    @Test
    public void dijkstra() {
        int a = random.nextInt(10), b = random.nextInt(10);
        while (a == b) {
            a = random.nextInt(10);
            b = random.nextInt(10);
        }
        System.out.println(a + "\t" + b);
        System.out.println(adjacentMatrix.dijkstra(a, b, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }));
    }

    @Test
    public void floyd() {
        int a = random.nextInt(10), b = random.nextInt(10);
        while (a == b) {
            a = random.nextInt(10);
            b = random.nextInt(10);
        }
        System.out.println(a + "\t" + b);
        System.out.println(adjacentMatrix.floyd(a, b, false, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        }));
    }
}