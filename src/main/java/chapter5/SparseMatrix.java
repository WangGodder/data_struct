package chapter5;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 稀疏矩阵
 * @param <T>
 */
public class SparseMatrix<T> {
    private int row;
    private int col;
    private double sparseProp;
    private TrebleTuple<T, Integer, Integer>[]elements;
    private boolean sorted = false;
    private char primaryKey = 'r';

    public SparseMatrix(int row,int col, double sparseProp) {
        this.sparseProp = sparseProp;
        this.row = row;
        this.col = col;
        int length = (int)(row * col * sparseProp);
        elements = (TrebleTuple<T, Integer, Integer>[])Array.newInstance(TrebleTuple.class, length);
    }

    public SparseMatrix(int row, int col, TrebleTuple<T, Integer, Integer>[] elements) {
        this.elements = elements;
        this.row = row;
        this.col = col;
    }

    public void T() {
        if (!sorted) {
            for (TrebleTuple<T, Integer, Integer> e : elements) {
                Integer tmp = e.getTwo();
                e.setTwo(e.getThree());
                e.setThree(tmp);
            }
        } else {
            int length;
            if (primaryKey == 'r') {
                length = col;
                primaryKey = 'c';
            } else {
                length = row;
                primaryKey = 'r';
            }
            int[] noZeroNum = new int[length];
            for (int i : noZeroNum) {
                i = 0;
            }
            for (TrebleTuple<T, Integer, Integer> e : elements) {
                noZeroNum[e.getThree()]++;
            }
            int[] index = new int[length];
            index[0] = 0;
            for (int i = 1; i < length; i++) {
                index[i] = index[i - 1] + noZeroNum[i - 1];
            }
            TrebleTuple<T, Integer, Integer>[] newElements =
                    (TrebleTuple<T, Integer, Integer>[])Array.newInstance(TrebleTuple.class, elements.length);
            for (TrebleTuple<T, Integer, Integer> e : elements) {
                noZeroNum[e.getThree()]--;
//                Integer tmp = e.getTwo();
//                e.setTwo(e.getThree());
//                e.setThree(tmp);
                newElements[index[e.getThree()] + noZeroNum[e.getThree()]] =
                        new TrebleTuple<T, Integer, Integer>(e.getOne(), e.getThree(), e.getTwo());
            }
            this.elements = newElements;
            System.gc();
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double getSparseProp() {
        return sparseProp;
    }

    public boolean isSorted() {
        return sorted;
    }

    public char getPrimaryKey() {
        return primaryKey;
    }

    public void setSorted(boolean sorted) {
        if (sorted != this.sorted) {
            sort();
        }
        this.sorted = sorted;
    }

    private void sort() {
        Arrays.sort(elements, new Comparator<TrebleTuple<T, Integer, Integer>>() {
            public int compare(TrebleTuple<T, Integer, Integer> o1, TrebleTuple<T, Integer, Integer> o2) {
                return o1.getTwo().compareTo(o2.getTwo());
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            TrebleTuple<T, Integer, Integer> e = elements[i];
            try {
                sb.append(e.toString());
            } catch (NullPointerException exception) {
                System.out.println("下标为" + i + "的元素为空\n");
                exception.printStackTrace();
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
