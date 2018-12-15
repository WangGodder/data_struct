package chapter6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class TestUtilsTest {
    private static Random random;

    @Before
    public void init() {
        random = new Random(System.currentTimeMillis());
    }

    @Test
    public void randomNextInt() {
        for (int i = 1; i < 100; i++) {
            int r = random.nextInt(i) + 1;
            boolean flag = r > 0 && r <= i;
            assert flag;
        }
    }

    @Test
    public void generateRandomBinNode() {
        BinNode<Integer> root = TestUtils.generateRandomBinNode(8);
        System.out.println(root);
    }
}