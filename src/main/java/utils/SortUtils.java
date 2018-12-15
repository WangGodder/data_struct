package utils;

import java.util.*;

/**
 * @author: godder
 * @date: 2018/12/13
 */
public class SortUtils {

    public static<T> Stack<Map.Entry<Integer, T>> sortArrayWithIndex(T[] array, boolean ascending,Comparator<T> comparator) {
        Stack<Map.Entry<Integer, T>> stack = new Stack<>();
        Map.Entry<Integer, T> enties[] = new Map.Entry[array.length];
        for (int i = 0; i < array.length; i++) {
            enties[i] = new AbstractMap.SimpleEntry<>(new Integer(i), array[i]);
        }
        Arrays.sort(enties, new Comparator<Map.Entry<Integer, T>>() {
            @Override
            public int compare(Map.Entry<Integer, T> o1, Map.Entry<Integer, T> o2) {
                return comparator.compare(o1.getValue(), o2.getValue());
            }
        });
        if (ascending) {
            for (int i = 0; i < array.length; i++) {
                stack.push(enties[i]);
            }
        } else {
            for (int i = array.length - 1; i >= 0; i--) {
                stack.push(enties[i]);
            }
        }
        return stack;
    }

    public static<T extends Comparable<T>> Stack<Map.Entry<Integer, T>> sortArrayWithIndex(T[] array, boolean ascending) {
        Stack<Map.Entry<Integer, T>> stack = new Stack<>();
        Map.Entry<Integer, T> enties[] = new Map.Entry[array.length];
        for (int i = 0; i < array.length; i++) {
            enties[i] = new AbstractMap.SimpleEntry<>(new Integer(i), array[i]);
        }
        Arrays.sort(enties, new Comparator<Map.Entry<Integer, T>>() {
            @Override
            public int compare(Map.Entry<Integer, T> o1, Map.Entry<Integer, T> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        if (ascending) {
            for (int i = 0; i < array.length; i++) {
                stack.push(enties[i]);
            }
        } else {
            for (int i = array.length - 1; i >= 0; i--) {
                stack.push(enties[i]);
            }
        }
        return stack;
    }

}
