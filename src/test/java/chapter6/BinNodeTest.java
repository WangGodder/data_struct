package chapter6;

import org.junit.Before;
import org.junit.Test;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class BinNodeTest {
    private BinNode<Integer> root;

    @Before
    public void setUp() throws Exception {
        root = TestUtils.generateRandomBinNode(6);
//        root = new BinNode<>(19);
//        root.setRight(new BinNode<>(28));
//        root.setLeft(new BinNode<>(16));
//        root.getRight().setLeft(new BinNode<>(26));
    }

    @Test
    public void preoderTraverse() {
        System.out.println("test preoderTraverse");
        System.out.println(root.preoderTraverse(true,0));
    }

    @Test
    public void inoderTraverse() {
        System.out.println("test inoderTraverse");
        System.out.println(root.inoderTraverse(true,0));
    }

    @Test
    public void postorderTraverse() {
        System.out.println("test postorderTraverse");
        System.out.println(root.postorderTraverse(true, 0));
    }

    @Test
    public void getChildren() {
    }

}