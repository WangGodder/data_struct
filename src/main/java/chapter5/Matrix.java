package chapter5;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Array;

public class Matrix<T> {
    private int col;
    private int row;
    private T[] data;
    private Operation<T> operation;
    private Class clazz;

    public Matrix(int row, int col, Class clazz) {
        this.col = col;
        this.row = row;
        this.clazz = clazz;
        this.data = (T[])Array.newInstance(clazz, row * col);
    }

    public Matrix(T[][] array2D, Operation<T> operation) {
        if (array2D.length == 0)
            throw new NullPointerException("传入数组为空");
        this.row = array2D.length;
        this.col = array2D[0].length;
        this.operation = operation;
        this.clazz = array2D[0][0].getClass();
        this.data = (T[])Array.newInstance(clazz, row * col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data[i * col + j] = array2D[i][j];
            }
        }
    }

    public Matrix(T[][] array2D) {
        if (array2D.length == 0)
            throw new NullPointerException("传入数组为空");
        this.row = array2D.length;
        this.col = array2D[0].length;
        this.clazz = array2D[0][0].getClass();
        this.data = (T[])Array.newInstance(clazz, row * col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data[i * col + j] = array2D[i][j];
            }
        }
    }

    public Matrix(T[] data, int row, int col) {
        this.data = data;
        this.row = row;
        this.col = col;
        this.clazz = data[0].getClass();
    }

    public Matrix(T[] data, int row, int col, Operation<T> operation) {
        this.data = data;
        this.row = row;
        this.col = col;
        this.clazz = data[0].getClass();
        this.operation = operation;
    }

    private Matrix(T[] data, Operation<T> operation, Class clazz, int row, int col) {
        this.data = data;
        this.operation = operation;
        this.clazz = clazz;
        this.row = row;
        this.col = col;
    }

    public void T() {
        T[] newData = (T[])Array.newInstance(clazz, row * col);
        for (int i = 0; i < col * row; i++) {
            int currentRow = i / col;
            int currentCol = i % col;
            int newIndex = currentCol * row + currentRow;
            newData[newIndex] = data[i];
        }
        data = newData;
        int tmp = row;
        row = col;
        col = tmp;
    }

    public void add(@NotNull Matrix<T> other) {
        if (this.row != other.getRow() || this.col != other.getCol()) {
            throw new RuntimeException("两个矩阵维度不同");
        }
        if (this.operation == null && other.getOperation() == null) {
            throw new NullPointerException("缺少定义矩阵成员算子");
        }

        T[] otherData = other.getData();
        Operation<T> op = (this.operation == null)? other.getOperation() : this.operation;
        for (int i  = 0; i < row * col; i++) {
            this.data[i] = op.add(this.data[i], otherData[i]);
        }
    }

    public void minu(@NotNull Matrix<T> other) {
        if (this.row != other.getRow() || this.col != other.getCol()) {
            throw new RuntimeException("两个矩阵维度不同");
        }
        if (this.operation == null && other.getOperation() == null) {
            throw new NullPointerException("缺少定义矩阵成员算子");
        }

        T[] otherData = other.getData();
        Operation<T> op = (this.operation == null)? other.getOperation() : this.operation;
        for (int i  = 0; i < row * col; i++) {
            this.data[i] = op.minu(this.data[i], otherData[i]);
        }
    }

    public Matrix<T> mult(@NotNull Matrix<T> other) {
        if (this.getCol() != other.getRow()) {
            throw new RuntimeException("两个矩阵维度不满足乘法要求");
        }
        if (this.operation == null && other.getOperation() == null) {
            throw new NullPointerException("缺少定义矩阵成员算子");
        }
        Operation<T> op = (this.operation == null)? other.getOperation() : this.operation;
        T[] newData = (T[])Array.newInstance(clazz, this.row * other.getCol());
        for (int i = 0; i < row; i++) {
            T[] rowLine = this.getOneRow(i);
            for (int j = 0; j < other.getCol(); j++) {
                newData[i * other.getCol() + j] = mutiSum(rowLine, other.getOneCol(j), op);
            }
        }
        return new Matrix<T>(newData, op, clazz, row, other.getCol());
    }

    public void scalarMult(T scalar) {
        if (this.operation == null) {
            throw new NullPointerException("没有定义类型算子");
        }
        for (T e : data) {
            e = operation.muti(scalar, e);
        }
    }

    /**
     * 判断是否为对称矩阵
     * @return
     */
    public boolean isSymmetry() {
        if (this.row != this.col) {
            return false;
        }
        for (int i = 0; i < row - 1; i++) {
            for (int j = i + 1; j < col; j++) {
                if (!this.get(i, j).equals(this.get(j, i))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < row * col; i++) {
            sb.append(data[i].toString());
            if ((i + 1) % col == 0)
                sb.append('\n');
            else
                sb.append(',');
        }
        return sb.toString();
    }

    public Matrix<T> copy() {
        return new Matrix<T>(this.data, operation, clazz, row, col);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Operation<T> getOperation() {
        return operation;
    }

    public Class getClazz() {
        return clazz;
    }

    public T get(int row, int col) {
        if (row >= this.row || col >= this.col || row < 0 || col < 0) {
            throw new ArrayIndexOutOfBoundsException("indexes should smaller than row and col and be positive");
        }
        return this.data[row * this.col + col];
    }

    public T[] getData() {
        T[] result = (T[])Array.newInstance(clazz, row * col);
        System.arraycopy(data, 0, result, 0, row * col);
        return result;
    }

    public T[] getOneRow(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("行数下标不能小于0");
        }
        T[] result = (T[])Array.newInstance(this.clazz, this.col);
        System.arraycopy(data, index * col, result, 0, col);
        return result;
    }

    public T[] getOneCol(int index) {
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException("列数下标不能小于0");
        }
        T[] result = (T[])Array.newInstance(this.clazz, this.row);
        for (int i = index, j = 0; i < row * col; i += col) {
            result[j++] = data[i];
        }
        return result;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setData(T[] data) {
        this.data = data;
    }

    public void set(int row, int col, T value) {
        if (row >= this.row || col >= this.col || row < 0 || col < 0) {
            throw new ArrayIndexOutOfBoundsException("indexes should smaller than row and col and be positive");
        }
        this.data[row * this.col + col] = value;
    }

    public void setOperation(Operation<T> operation) {
        this.operation = operation;
    }

    private T mutiSum(T[] arr1, T[] arr2, Operation<T> op) {
        if (arr1.length != arr2.length) {
            throw new RuntimeException("进行乘积累加运算的俩个参数长度不匹配");
        }
        if (arr1.length == 0) {
            return null;
        }
        T result = op.muti(arr1[0], arr2[0]);
        for (int i = 1; i < arr1.length; i++) {
            result = op.add(result, op.muti(arr1[i], arr2[i]));
        }
        return result;
    }
}
