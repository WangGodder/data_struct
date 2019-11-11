package sort;

import tree.MinHeap;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: godder
 * @date: 2019/11/6
 */
public class Sorts {
    /**
     * Sort the numbers in input file and output result to output file.
     * @param inputFile A File object which connects the input file.
     * @param outputFile A File object which connected the output file.
     * @param splitSymbol The symbol to split the number in input file.
     * @throws IOException When input file can not be found or be read, and the output file can not be written.
     */
    public static void fileSort(File inputFile, File outputFile, String splitSymbol) throws IOException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile));
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile));
        splitSymbol = splitSymbol.isEmpty() ? "," : splitSymbol;
        ArrayList<Double> arrayList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int tmp;
        while ((tmp = reader.read()) != -1) {
            // skip '\n' symbol
            if (tmp == (int) '\n') {
                continue;
            }
            stringBuilder.append((char) tmp);
        }
        String fileString = stringBuilder.toString();
        for (String numberString : fileString.split(splitSymbol)) {
            arrayList.add(Double.parseDouble(numberString));
        }
        // sort and store
        Double array[] = new Double[arrayList.size()];
        arrayList.toArray(array);
        Arrays.sort(array);
        stringBuilder = new StringBuilder();
        for (Double number : array) {
            stringBuilder.append(Double.toString(number));
            stringBuilder.append(",");
        }
        writer.write(stringBuilder.toString());
        writer.close();
    }

    /**
     * sort a array by merge sorting
     * @param array the array to sort
     * @param <T> the type of array
     */
    public static <T extends Comparable<T>> void mergeSort(@NotNull T[] array) {
        List<T> list = Arrays.asList(array);
        list = splitMerge(list);
        list.toArray(array);
    }

    private static <T extends Comparable<T>> List<T> merge(@NotNull List<T> list1, @NotNull List<T> list2) {
        if (list1.size() > 1) {
            list1 = splitMerge(list1);
        }
        if (list2.size() > 1) {
            list2 = splitMerge(list2);
        }
        ArrayList<T> tArrayList = new ArrayList<>(list1.size() + list2.size());
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i).compareTo(list2.get(j)) > 0) {
                tArrayList.add(list2.get(j++));
            } else {
                tArrayList.add(list1.get(i++));
            }
        }
        while (i < list1.size()) {
            tArrayList.add(list1.get(i++));
        }
        while (j < list2.size()) {
            tArrayList.add(list2.get(j++));
        }
        return tArrayList;
    }

    private static <T extends Comparable<T>> List<T> splitMerge(@NotNull List<T> list){
        ArrayList<T> leftList = new ArrayList<>(list.size() / 2);
        for (int i = 0; i < (int)list.size() / 2; i++) {
            leftList.add(list.get(i));
        }
        ArrayList<T> rightList = new ArrayList<>(list.size() / 2 + 1);
        for (int i = leftList.size(); i < list.size(); i++) {
            rightList.add(list.get(i));
        }
        return merge(leftList, rightList);
    }

    public static <T extends Comparable<T>> void heapSort(@NotNull T[] array) {
        MinHeap<T> heap = new MinHeap<>();
        for (T n : array) {
            heap.insert(n);
        }
        List<T> list = new ArrayList<>(array.length);
        for (int i = 0; i < array.length; i++) {
            list.add(heap.pop());
        }
        list.toArray(array);
    }
}
