package chapter5;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class MatrixTest {
    private Matrix<Integer> matrix1, matrix2;

    @Before
    public void setUp() throws Exception {
        matrix1 = TestUitls.generateIntegerMatrix(4, 5);
        System.out.println(matrix1);
        matrix2 = TestUitls.generateIntegerMatrix(5, 6);
        System.out.println(matrix2);
    }

    @Test
    public void add() {
    }

    @Test
    public void minu() {
    }

    @Test
    public void mult() {
        System.out.println(matrix1.mult(matrix2));
    }

    @Test
    public void scalarMult() {
        matrix1.scalarMult(10);
        System.out.println(matrix1);
    }

    @Test
    public void isSymmetry() {
        System.out.println(matrix1.isSymmetry());
    }
}