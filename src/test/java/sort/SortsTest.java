package sort;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author: godder
 * @date: 2019/11/6
 */
public class SortsTest {

    @Test
    public void fileSort() {
        File inputFile = new File("F:\\test_project\\datastruct\\src\\test\\java\\sort/input.txt");
        File outputFile = new File("F:\\test_project\\datastruct\\src\\test\\java\\sort/output.txt");
        try {
            Sorts.fileSort(inputFile, outputFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mergeSort() {
        Integer[] nums = {3, 4, 1, 2 , 6};
        Sorts.mergeSort(nums);
        for (Integer n : nums) {
            System.out.println(n);
        }
    }

    @Test
    public void heapSort() {
        Integer[] nums = {3, 4, 1, 2 , 6};
        Sorts.heapSort(nums);
        for (Integer n : nums) {
            System.out.println(n);
        }
    }
}