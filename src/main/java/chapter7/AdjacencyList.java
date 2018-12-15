package chapter7;

import chapter5.TrebleTuple;

import java.util.*;

/**
 * @author: godder
 * @date: 2018/12/12
 */

/**
 * 邻接列表表示图
 * @param <V>   边的权重
 * @param <E>   点的值
 */
public class AdjacencyList<V, E> {
    private ArrayList<TrebleTuple<Integer, E, AdjacencyNode<V>>> adjacencyList;
    private boolean isNumber = false;
    private int numEdge = 0;
    private boolean isConnected = true;

    /**
     * 构建邻接列表
     * @param values    点值列表
     * @param vectorMatrix  表示边的的二维向量
     */
    public AdjacencyList(V[][] vectorMatrix, List<E> values) {
        if (vectorMatrix.length != vectorMatrix[0].length || vectorMatrix.length != values.size()) {
            throw new RuntimeException("dim wrong, vector matrix should be a square matrix");
        }
        this.adjacencyList = new ArrayList<>(vectorMatrix.length);
        this.isNumber = vectorMatrix[0][0].getClass().getSuperclass() == Number.class;
        for (int i = 0; i < vectorMatrix.length; i++) {
            AdjacencyNode<V> node = null;
            for (int j = vectorMatrix.length - 1; j >= 0; j--) {
                if (j == i) {
                    continue;
                }
                if (vectorMatrix[i][j] == null || (isNumber && vectorMatrix[i][j].equals(0))) {
                    continue;
                }

                node = new AdjacencyNode<>(j, vectorMatrix[i][j], node);
                numEdge++;

            }
            this.adjacencyList.add(new TrebleTuple<>(i, values.get(i), node));
        }
    }

    public AdjacencyList(V[] vectorArray, List<E> values) {
        final double eps = 1e-10;
        double length = Math.sqrt(vectorArray.length);
        if (length - Math.floor(length) >= eps || (int)length != values.size()) {
            throw new RuntimeException("dim wrong, vector array length should be a number's square not " + length);
        }
        this.adjacencyList = new ArrayList<>((int)length);
        this.isNumber = vectorArray[0].getClass().getSuperclass() == Number.class;
        for (int i = 0; i < (int)length; i++) {
            AdjacencyNode<V> node = null;
            for (int j = (int)length - 1; j >= 0; j--) {
                if (j == i) {
                    continue;
                }
                if (vectorArray[i * (int)length + j] == null || (isNumber && vectorArray[i * (int)length + j].equals(0))) {
                    continue;
                }
                node = new AdjacencyNode<>(j, vectorArray[i * (int)length + j], node);
                numEdge++;
            }
            this.adjacencyList.add(new TrebleTuple<>(i, values.get(i), node));
        }
    }

    /**
     * 邻接列表的图的深度优先遍历
     * @param beginIndex
     * @return
     */
    public List<Map.Entry<Integer, E>> deepthFirstSearch(int beginIndex) {
        if (beginIndex >= this.adjacencyList.size()) {
            throw new ArrayIndexOutOfBoundsException("begin index should not bigger than value list size " + this.adjacencyList.size());
        }
        boolean isSearched[] = new boolean[this.adjacencyList.size()];
        int searchNum = 0;
        List<Map.Entry<Integer, E>> result = new ArrayList<>(this.adjacencyList.size());
        while ((searchNum = deepthRowSearch(beginIndex, searchNum, isSearched, result)) < this.adjacencyList.size()) {
            this.isConnected = false;
            for (int i = 0; i < isSearched.length; i++) {
                if (!isSearched[i]) {
                    beginIndex = i;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 深度优先遍历子函数
     * @param rowNum
     * @param searchNum
     * @param isSearched
     * @param result
     * @return
     */
    private int deepthRowSearch(int rowNum, int searchNum, boolean[] isSearched, List<Map.Entry<Integer, E>> result) {
        if (!isSearched[rowNum]) {
            result.add(new AbstractMap.SimpleEntry<>(rowNum, this.adjacencyList.get(rowNum).getTwo()));
            searchNum++;
            isSearched[rowNum] = true;
        }
        AdjacencyNode<V> node = this.adjacencyList.get(rowNum).getThree();
        while (node != null && searchNum < this.adjacencyList.size()) {
            if (isSearched[node.index]) {
                node = node.next;
                continue;
            }
            searchNum = deepthRowSearch(node.index, searchNum, isSearched, result);
            node = node.next;
        }
        return searchNum;
    }

    /**
     * 邻接列表广度优先收索
     * @param beginIndex
     * @return
     */
    public List<Map.Entry<Integer, E>> breadthFirstSearch(int beginIndex) {
        if (beginIndex >= this.adjacencyList.size()) {
            throw new ArrayIndexOutOfBoundsException("begin index should not bigger than value list size " + this.adjacencyList.size());
        }
        boolean isSearched[] = new boolean[this.adjacencyList.size()];
        int searchNum = 0;
        List<Map.Entry<Integer, E>> result = new ArrayList<>(this.adjacencyList.size());
        while ((searchNum = breadthRowSearch(beginIndex, searchNum, isSearched, result)) < this.adjacencyList.size()) {
            this.isConnected = false;
            for (int i = 0; i < isSearched.length; i++) {
                if (!isSearched[i]) {
                    beginIndex = i;
                    break;
                }
            }
        }
        return  result;
    }

    /**
     * 广度优先收索子函数
     * @param rowNum
     * @param searchNum
     * @param isSearched
     * @param result
     * @return
     */
    private int breadthRowSearch(int rowNum, int searchNum, boolean[] isSearched, List<Map.Entry<Integer, E>> result) {
        if (!isSearched[rowNum]) {
            result.add(new AbstractMap.SimpleEntry<>(rowNum, this.adjacencyList.get(rowNum).getTwo()));
            searchNum++;
            isSearched[rowNum] = true;
        }
        AdjacencyNode<V> node = this.adjacencyList.get(rowNum).getThree();
        List<Integer> nextIndexes = new LinkedList<>();
        while (node != null && searchNum < this.adjacencyList.size()) {
            if (isSearched[node.index]) {
                node = node.next;
                continue;
            }
            result.add(new AbstractMap.SimpleEntry<>(node.index, this.adjacencyList.get(node.index).getTwo()));
            searchNum++;
            isSearched[node.index] = true;
            nextIndexes.add(node.index);
            node = node.next;
        }
        for (Integer index : nextIndexes) {
            searchNum = breadthRowSearch(index, searchNum, isSearched, result);
        }
        return searchNum;
    }

    public int getNumEdge() {
        return numEdge;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TrebleTuple<Integer, E, AdjacencyNode<V>> trebleTuple : adjacencyList) {
            sb.append(trebleTuple.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

