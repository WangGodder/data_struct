package chapter7;

import chapter5.Matrix;
import chapter5.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static chapter6.TestUtils.randomString;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class TestUtils {
    public static final float CONNECT_PROBABILITY = 1.155f; // 0.75  in gaussian distribution
    public static final int BOUND = 100;
    private static Random random = new Random(System.currentTimeMillis());

    public static AdjacencyList<Integer, String> generateAdjacencyListG(int nodeNum) {
        Integer vectorMatrix[][] = new Integer[nodeNum][nodeNum];
        List<String> valueList = new ArrayList<>(nodeNum);
        for (int i = 0; i < nodeNum; i++) {
            for (int j = i; j < nodeNum; j++) {
                if (Math.abs(random.nextGaussian()) > CONNECT_PROBABILITY) {
                    vectorMatrix[i][j] = 0;
                    vectorMatrix[j][i] = 0;
                } else {
                    vectorMatrix[i][j] = random.nextInt(BOUND);
                    vectorMatrix[j][i] = vectorMatrix[i][j];
                }
            }
            valueList.add(randomString(nodeNum/2));
        }
        return new AdjacencyList<>(vectorMatrix, valueList);
    }

    public static AdjacencyList<Integer, String> generateAdjacencyListD(int nodeNum) {
        Integer vectorMatrix[][] = new Integer[nodeNum][nodeNum];
        List<String> valueList = new ArrayList<>(nodeNum);
        for (int i = 0; i < nodeNum; i++) {
            for (int j = 0; j < nodeNum; j++) {
                if (Math.abs(random.nextGaussian()) > CONNECT_PROBABILITY) {
                    vectorMatrix[i][j] = 0;
                } else {
                    vectorMatrix[i][j] = random.nextInt(BOUND);
                }
            }
            valueList.add(randomString(nodeNum/2));
        }
        return new AdjacencyList<>(vectorMatrix, valueList);
    }

    public static AdjacentMatrix<Integer, String> generatreAdjacencyMatrixG(int nodeNum) {
        Integer vectorMatrix[][] = new Integer[nodeNum][nodeNum];
        List<String> valueList = new ArrayList<>(nodeNum);
        for (int i = 0; i < nodeNum; i++) {
            for (int j = i; j < nodeNum; j++) {
                if (Math.abs(random.nextGaussian()) > CONNECT_PROBABILITY) {
                    vectorMatrix[i][j] = 0;
                    vectorMatrix[j][i] = 0;
                } else {
                    vectorMatrix[i][j] = random.nextInt(BOUND);
                    vectorMatrix[j][i] = vectorMatrix[i][j];
                }
            }
            valueList.add(randomString(nodeNum/2));
        }
        return new AdjacentMatrix<>(new Matrix<Integer>(vectorMatrix, new Operation<Integer>() {
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
        }), valueList);
    }

    public static AdjacentMatrix<Integer, String> generatreAdjacencyMatrixD(int nodeNum) {
        Integer vectorMatrix[][] = new Integer[nodeNum][nodeNum];
        List<String> valueList = new ArrayList<>(nodeNum);
        for (int i = 0; i < nodeNum; i++) {
            for (int j = 0; j < nodeNum; j++) {
                if (Math.abs(random.nextGaussian()) > CONNECT_PROBABILITY) {
                    vectorMatrix[i][j] = 0;
                } else {
                    vectorMatrix[i][j] = random.nextInt(BOUND);
                }
            }
            valueList.add(randomString(nodeNum/2));
        }
        return new AdjacentMatrix<>(new Matrix<Integer>(vectorMatrix, new Operation<Integer>() {
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
        }), valueList);
    }
}
