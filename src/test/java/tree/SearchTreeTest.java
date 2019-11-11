package tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author: godder
 * @date: 2019/11/11
 */
public class SearchTreeTest {

    @Test
    public void insert() {
        SearchTree<Integer> st = new SearchTree<>();
        st.insert(3);
        st.insert(1);
        st.insert(5);
        st.insert(3);
        st.insert(4);
        System.out.println(st);
    }

    @Test
    public void delete() {
        SearchTree<Integer> st = new SearchTree<>();
        st.insert(3);
        st.insert(1);
        st.insert(5);
        st.insert(3);
        st.insert(4);
        System.out.println(st);
        System.out.println(st.delete(4));
        System.out.println(st);
    }

    @Test
    public void find() {
        List<Integer> list = Arrays.asList(new Integer[]{-15, -6, 0, 7, 9, 23, 54, 82, 101});
        SearchTree<Integer> st = new SearchTree<>();
        for (Integer i : list) {
            st.insert(i);
        }
        assert st.find(82);
        assert !st.find(-20);
        assert !st.find(6);
        st.delete(82);
        assert !st.find(82);
    }
}