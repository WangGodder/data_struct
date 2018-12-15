package chapter7;

import chapter5.Matrix;
import chapter5.Operation;

import java.io.Serializable;
import java.util.*;

import static utils.SortUtils.sortArrayWithIndex;

/**
 * @author: godder
 * @date: 2018/12/12
 */

/**
 * 用邻接矩阵表示图
 * @param <V>   边的权重
 * @param <E>   点的值
 */
public class AdjacentMatrix<V, E> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Matrix<V> adjacentMatrix;
    private List<E> valueList;
    private int numNode = 0;
    private int numEdge = -1;
    private boolean isNumber = false;
    private boolean isConnected = true;
    private boolean isCheckConnected = false;
    private int[][] path = null;

    public AdjacentMatrix(V[][] vectorMatrix, List<E> valueList) {
        if (vectorMatrix.length != vectorMatrix[0].length || vectorMatrix.length != valueList.size()) {
            throw new RuntimeException("dim wrong, vector matrix should be a square matrix");
        }
        init(new Matrix<>(vectorMatrix), valueList, false);
    }

    public AdjacentMatrix(V[][] vectorMatrix, List<E> valueList, Operation<V> operation) {
        if (vectorMatrix.length != vectorMatrix[0].length || vectorMatrix.length != valueList.size()) {
            throw new RuntimeException("dim wrong, vector matrix should be a square matrix");
        }
        init(new Matrix<>(vectorMatrix, operation), valueList, false);
    }

    public AdjacentMatrix(V[] vectorArray, List<E> valueList) {
        final double eps = 1e-10;
        double length = Math.sqrt(vectorArray.length);
        if (length - Math.floor(length) >= eps || (int)length != valueList.size()) {
            throw new RuntimeException("dim wrong, vector array length should be a number's square not " + length);
        }
        init(new Matrix<>(vectorArray, (int)length, (int)length), valueList, false);
    }

    public AdjacentMatrix(V[] vectorArray, List<E> valueList, Operation<V> operation) {
        final double eps = 1e-10;
        double length = Math.sqrt(vectorArray.length);
        if (length - Math.floor(length) >= eps || (int)length != valueList.size()) {
            throw new RuntimeException("dim wrong, vector array length should be a number's square not " + length);
        }
        init(new Matrix<>(vectorArray, (int)length, (int)length, operation), valueList, false);
    }

    public AdjacentMatrix(Matrix<V> vectorMatrix, List<E> valueList) {
        if (vectorMatrix.getRow() != vectorMatrix.getCol() || vectorMatrix.getRow() != valueList.size()) {
            throw new RuntimeException("dim wrong, vector matrix must be square");
        }
        init(vectorMatrix, valueList, false);
    }

    private void init(Matrix<V> matrix, List<E> list, boolean countNum) {
        this.numNode = list.size();
        this.adjacentMatrix = matrix;
        this.valueList = list;
        // 判断输入的邻接矩阵的泛型是否为数字类型的子类,数字类型的0不是null
        isNumber = this.adjacentMatrix.getClazz().getSuperclass() == Number.class;
        if (countNum) {
            countEdgeNum();
        }
    }

    private void countEdgeNum() {
        numEdge = 0;
        for (int i = 0; i < numNode - 1; i++) {
            for (int j = i + 1; j < numNode; j++) {
                if ((isNumber && !adjacentMatrix.get(i, j).equals(0)) || adjacentMatrix.get(i, j) != null) {
                    numEdge++;
                }
                if ((isNumber && !adjacentMatrix.get(j, i).equals(0)) || adjacentMatrix.get(j, i) != null) {
                    numEdge++;
                }
            }
        }
    }

    public AdjacencyList<V, E> toAdjacencyList() {
        AdjacencyList<V, E> adjacencyList = new AdjacencyList<>(this.adjacentMatrix.getData(), this.valueList);
        return adjacencyList;
    }

    /**
     * 指定节点的深度优先收索
     * @param beginIndex
     * @return  对应节点value的list
     */
    public List<Map.Entry<Integer, E>> deepthFirstSearch(int beginIndex) {
        if (beginIndex >= this.valueList.size()) {
            throw new ArrayIndexOutOfBoundsException("begin index should smaller than " + this.valueList.size());
        }
        boolean isSearched[] = new boolean[this.valueList.size()];
        List<Map.Entry<Integer, E>> result = new ArrayList<>(isSearched.length);
        int searchNum = 0;
        this.isCheckConnected = true;
        while ((searchNum = deepthRowSearch(beginIndex, searchNum, isSearched, result)) < valueList.size()) {
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
     * 深度优先收索的子函数，用于针对矩阵中一行进行递归搜索
     * @param rowNum
     * @param searchNum
     * @param isSearched
     * @param result
     * @return
     */
    private int deepthRowSearch(int rowNum, int searchNum, boolean[] isSearched, List<Map.Entry<Integer, E>> result) {
        if (!isSearched[rowNum]) {
            result.add(new AbstractMap.SimpleEntry<>(rowNum, this.valueList.get(rowNum)));
            searchNum++;
            isSearched[rowNum] = true;
        }
        V[] row = this.adjacentMatrix.getOneRow(rowNum);
        for (int i = 0; i < row.length; i++) {
            if (searchNum == this.valueList.size() || isSearched[rowNum]) {
                return searchNum;
            }
            if (i == rowNum) {
                continue;
            }
            if ((isNumber && row[i].equals(0)) || row[i] == null) {
                continue;
            }
            deepthRowSearch(i, searchNum, isSearched, result);
        }
        return searchNum;
    }

    /**
     * 图的广度优先收索
     * @param beginIndex 起始点下标
     * @return
     */
    public List<Map.Entry<Integer, E>> breadthFirstSearch(int beginIndex) {
        if (beginIndex >= this.valueList.size()) {
            throw new ArrayIndexOutOfBoundsException("begin index should smaller than " + this.valueList.size());
        }
        boolean isSearched[] = new boolean[this.valueList.size()];
        List<Map.Entry<Integer, E>> result = new ArrayList<>(isSearched.length);
        int searchNum = 0;
        this.isCheckConnected = true;
        while ((searchNum = breadthRowSearch(beginIndex, searchNum, isSearched, result)) < this.valueList.size()) {
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
     * 广度优先收索子函数
     * @param rowIndex
     * @param searchNum
     * @param isSearched
     * @param result
     * @return
     */
    private int breadthRowSearch(int rowIndex, int searchNum, boolean[] isSearched, List<Map.Entry<Integer, E>> result) {
        if (!isSearched[rowIndex]) {
            result.add(new AbstractMap.SimpleEntry<>(rowIndex, valueList.get(rowIndex)));
            isSearched[rowIndex] = true;
            searchNum++;
        }
        List<Integer> nextIndex = new ArrayList<>(this.valueList.size());
        V[] row = this.adjacentMatrix.getOneRow(rowIndex);
        for (int i = 0; i < row.length; i++) {
            if (searchNum == numNode) {
                return searchNum;
            }
            if (i == rowIndex) {
                continue;
            }
            if ((isNumber && row[i].equals(0)) || row[i] == null) {
                continue;
            }
            if (isSearched[i]) {
                continue;
            }
            result.add(new AbstractMap.SimpleEntry<>(i, this.valueList.get(i)));
            isSearched[i] = true;
            searchNum++;
            nextIndex.add(i);
        }

        for (Integer i : nextIndex) {
            searchNum = breadthRowSearch(i, searchNum, isSearched, result);
        }
        return searchNum;
    }

    /**
     * 获取全局最优最短路径。 建议使用floyd方法，由于时间损耗，本方法弃用
     * @param begin
     * @param end
     * @param comparator
     * @return
     */
    @Deprecated
    public List<Integer> getMinimumWay(int begin, int end, Comparator<V> comparator) {
        if (!isNumber && this.adjacentMatrix.getOperation() == null) {
            throw new RuntimeException("current type is not Number class, need a operation to operate");
        }
        if (!this.isCheckConnected) {
            System.err.println("whether this graph is connected is unchecked, please invoke method deepthFirstSearch() or breadthFirstSearch()");
        }
        if (begin == end) {
            return new LinkedList<>();
        }
        V[] row = this.adjacentMatrix.getOneRow(begin);
        Stack<Map.Entry<Integer, V>> stack = sortArrayWithIndex(row, false, comparator);
        V maxWeight = null;
        LinkedList<Integer> result = null;
        for (Map.Entry<Integer, V> entry : stack) {
            if ((isNumber && entry.getValue().equals(0)) || entry.getValue() == null) {
                continue;
            }
            boolean isVisited[] = new boolean[this.numNode];
            LinkedList<Integer> list = new LinkedList<>();
            isVisited[begin] = true;
            V totalWeight = getOneWay(entry.getKey(), end, entry.getValue(), maxWeight, isVisited, list, comparator);
            if (maxWeight == null) {
                maxWeight = totalWeight;
            } else {
                if (comparator.compare(totalWeight, maxWeight) < 0) {
                    result = list;
                    maxWeight = totalWeight;
                }
            }
        }

        return result;
    }

    private V getOneWay(int begin, int end, V totalWeight, V maxWeight, boolean[] isVisted, LinkedList<Integer> result, Comparator<V> comparator) {
        V[] row = this.adjacentMatrix.getOneRow(begin);
        Stack<Map.Entry<Integer, V>> stack = sortArrayWithIndex(row, false, comparator);
        for (int i = 0; i < row.length; i++) {
            Map.Entry<Integer, V> entry = stack.pop();
            if ((isNumber && entry.getValue().equals(0)) || entry.getValue() == null) {
                continue;
            }
            V currentTotal = this.adjacentMatrix.getOperation().add(totalWeight, entry.getValue());
            // 超过当前最大值，跳过下一个
            if (maxWeight != null && comparator.compare(currentTotal, maxWeight) > 0) {
                continue;
            }
            if (isVisted[entry.getKey()]) {
                continue;
            }
            result.add(entry.getKey());
            System.out.println(entry.getKey());
            isVisted[entry.getKey()] = true;
            totalWeight = currentTotal;
            // 成功找到目标
            if (entry.getKey() == end) {
                return totalWeight;
            }
            getOneWay(entry.getKey(), end, totalWeight, maxWeight, isVisted, result, comparator);
        }
        // 当前路径没有通向目标的路径，删除当前子路径
        System.out.println("return");
        if (!result.isEmpty()) {
            System.out.println("remove last");
            result.removeLast();
        }
        return totalWeight;
    }

    /**
     * dijkstra算法，单源最短路径算法，贪心算法，非最优解. time:O（n^2) space:O(n^2)
     * @param begin
     * @param end
     * @param comparator
     * @return 访问点的下标列表，null为无法连接
     */
    public List<Integer> dijkstra(int begin, int end, Comparator<V> comparator) {
        if (!isNumber && this.adjacentMatrix.getOperation() == null) {
            throw new RuntimeException("current type is not Number class, need a operation to operate");
        }
        LinkedList<Integer> rount = new LinkedList<>();
        boolean isVisited[] = new boolean[numNode];
        rount.add(begin);
        isVisited[begin] = true;
        if (dijkstra(begin, end, isVisited, rount, comparator)) {
            return rount;
        }
        return null;
    }

    /**
     * dijkstra 算法子函数， 返回是否可以连通到目标
     * @param begin
     * @param end
     * @param isVisited
     * @param rount
     * @param comparator
     * @return
     */
    private boolean dijkstra(int begin, int end, boolean[] isVisited, LinkedList<Integer> rount, Comparator<V> comparator) {
        V[] row = this.adjacentMatrix.getOneRow(begin);
        Stack<Map.Entry<Integer, V>> stack = sortArrayWithIndex(row, false, comparator);
        boolean isContinue = true;
        while (isContinue) {
            if (stack.isEmpty()) {
                // 错误路径，不连通目标节点
                isVisited[rount.getLast()] = false;
                rount.removeLast();
                return false;
            }
            Map.Entry<Integer, V> entry = stack.pop();
            // 不连通
            if ((isNumber && entry.getValue().equals(0)) || entry.getValue() == null) {
                continue;
            }
            begin = entry.getKey();
            if (isVisited[begin]) {
                continue;
            }
            rount.addLast(begin);
            isVisited[begin] = true;
            if (begin == end) {
                return true;
            }
            isContinue = !dijkstra(begin, end, isVisited, rount, comparator);
        }
        return true;
    }

    /**
     * 初始化floyd 的 path矩阵
     * @param print
     * @param comparator
     */
    public void initFloyd(boolean print, Comparator<V> comparator) {
        if (!isNumber && this.adjacentMatrix.getOperation() == null) {
            throw new RuntimeException("current type is not Number class, need a operation to operate");
        }
        // 记录每俩个点之间的连接权值
        Matrix<V> connectWeigths = this.adjacentMatrix.copy();
        // 记录两个点之间连接通过的第三点，直连则为-1
        this.path = new int[numNode][numNode];
        Operation<V> op = this.adjacentMatrix.getOperation();
        for (int i = 0; i < numNode; i++) {
            for (int j = 0; j < numNode; j++) {
                path[i][j] = -1;
            }
        }
        for (int k = 0; k < numNode; k++) {
            for (int i = 0; i < numNode; i++) {
                for (int j = 0; j < numNode; j++) {
                    if (isNumber && (connectWeigths.get(i, k).equals(0) || connectWeigths.get(k, j).equals(0))) {
                        continue;
                    }
                    if (connectWeigths.get(i, k) == null || connectWeigths.get(k, j) == null) {
                        continue;
                    }
                    V sum = op.add(connectWeigths.get(i, k), connectWeigths.get(k, j));
                    if ((isNumber && connectWeigths.get(i, j).equals(0)) || connectWeigths.get(i, j) == null || comparator.compare(connectWeigths.get(i, j), sum) > 0) {
                        connectWeigths.set(i, j, sum);
                        path[i][j] = k;
                    }
                }
            }
        }
        if (print) {
            System.out.println(connectWeigths);
            for (int[] i : path) {
                System.out.println(Arrays.toString(i));
            }
        }
    }

    /**
     * floyd 算法， 求两个点的最短路径 全局最优  time:O(n^3), space: O(n^2)
     * @param begin
     * @param end
     * @param reloadPath    是否重新计算path路径，如果不重新计算则会快速从之前获得的path矩阵中获取路径，path为null时自动
     * @param comparator
     * @return  返回下标list，如果俩个点不连通则返回null
     */
    public List<Integer> floyd(int begin, int end, boolean reloadPath, Comparator<V> comparator) {
        if (this.path == null || reloadPath) {
            initFloyd(false, comparator);
        }
        LinkedList<Integer> rount = new LinkedList<>();
        rount.add(begin);
        int skip;
        while ((skip = path[begin][end]) != -1 && begin != end) {
            if ((isNumber && adjacentMatrix.get(begin, end).equals(0)) || adjacentMatrix.get(begin, end) == null) {
                return null;
            }
            rount.addLast(skip);
            begin = skip;
        }
        rount.addLast(end);
        return rount;
    }

    public Matrix<V> getAdjacentMatrix() {
        return adjacentMatrix;
    }

    public List<E> getValueList() {
        return valueList;
    }

    public int getNumNode() {
        return numNode;
    }

    public int getNumEdge() {
        if (numEdge < 0) {
            countEdgeNum();
        }
        return numEdge;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public boolean isConnected() {
        if (!this.isCheckConnected) {
            System.err.println("whether this graph is connected is unchecked, please invoke method deepthFirstSearch() or breadthFirstSearch()");
        }
        return isConnected;
    }

    public boolean isCheckConnected() {
        return isCheckConnected;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.valueList);
        sb.append("\n");
        sb.append(this.adjacentMatrix);
        return sb.toString();
    }

}
