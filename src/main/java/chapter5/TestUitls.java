package chapter5;

import java.util.Random;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class TestUitls {
    private static Random random = new Random(System.currentTimeMillis());

    public static Matrix<Integer> generateIntegerMatrix(int row, int col) {
        Integer array[] = new Integer[row * col];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        return new Matrix<Integer>(array, row, col, new Operation<Integer>() {
            @Override
            public Integer add(Integer o1, Integer o2) {
                return o1 + o2;
            }

            @Override
            public Integer minu(Integer o1, Integer o2) {
                return o1 - o2;
            }

            @Override
            public Integer muti(Integer o1, Integer o2) {
                return o1 * o2;
            }

            @Override
            public Integer divide(Integer o1, Integer o2) {
                return o1 / o2;
            }
        });
    }

    public static Matrix<Double> generateDoubleMatrix(int row, int col) {
        Double array[] = new Double[row * col];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextDouble();
        }
        return new Matrix<Double>(array, row, col, new Operation<Double>() {
            @Override
            public Double add(Double o1, Double o2) {
                return o1 + o2;
            }

            @Override
            public Double minu(Double o1, Double o2) {
                return o1 - o2;
            }

            @Override
            public Double muti(Double o1, Double o2) {
                return o1 * o2;
            }

            @Override
            public Double divide(Double o1, Double o2) {
                return o1 / o2;
            }
        });
    }
}
