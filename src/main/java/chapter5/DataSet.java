package chapter5;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DataSet<T> {
    private int setSize;
    private List<T> data;

    public DataSet() {
        setSize = 0;
        data = new LinkedList<T>();
    }
    public DataSet(T[] array) {
        setSize = array.length;
        data = Arrays.asList(array);
    }

    public int size() {
        return setSize;
    }

    public boolean insert(T obj) {
        try {
            data.add(obj);
            setSize++;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean remove(T obj) {
        try {
            data.remove(obj);
            setSize--;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public T get(int index) {
        try {
            return data.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(index + "超出数组界限");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (T d : data) {
            sb.append(d.toString());
            sb.append('\n');
        }
        sb.append('}');
        return sb.toString();
    }
}
