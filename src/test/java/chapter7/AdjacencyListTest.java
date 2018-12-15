package chapter7;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author: godder
 * @date: 2018/12/14
 */
    public class AdjacencyListTest {
    private AdjacencyList<Integer, String> adjacencyList;
    private Random random;

    @Before
    public void setUp() throws Exception {
        adjacencyList = TestUtils.generateAdjacencyListG(10);
        System.out.println(adjacencyList);
        random = new Random(System.currentTimeMillis());
    }

    @Test
    public void deepthFirstSearch() {
        System.out.println(adjacencyList.deepthFirstSearch(random.nextInt(10)));
    }

    @Test
    public void breadthFirstSearch() {
        System.out.println(adjacencyList.breadthFirstSearch(random.nextInt(10)));
    }
}