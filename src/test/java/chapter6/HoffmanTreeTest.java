package chapter6;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class HoffmanTreeTest {
    private String string;

    @Before
    public void setUp() throws Exception {
        string = TestUtils.randomString(100);
        System.out.println(string);
    }

    @Test
    public void code() {
        System.out.println(HoffmanTree.code(string, 3));
    }

    @Test
    public void statisticChar() {
        System.out.println(HoffmanTree.statisticChar(string));
    }

    @Test
    public void createTree() {

//        System.out.println(HoffmanTree.createTree(HoffmanTree.statisticChar(string), 3));
    }

    @Test
    public void encodeHuffman() {

    }
}