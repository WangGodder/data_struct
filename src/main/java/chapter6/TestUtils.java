package chapter6;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author: godder
 * @date: 2018/12/14
 */
public class TestUtils {
    private static Random random = new Random(System.currentTimeMillis());

    public static BinNode<Integer> generateRandomBinNode(int nodeNum) {
        BinNode<Integer> root = new BinNode<>(random.nextInt());
        nodeNum--;
        List<BinNode<Integer>> leafNodes = new LinkedList<>();
        leafNodes.add(root);
        while (nodeNum > 0) {
            int pos = random.nextInt(leafNodes.size()*2);
            BinNode<Integer> currentNode = leafNodes.get(pos/2), newNode = new BinNode<>(random.nextInt());
            if (pos % 2 == 0) {
                currentNode.setLeft(newNode);
            } else {
                currentNode.setRight(newNode);
            }
            if (currentNode.getLeft() != null && currentNode.getRight() != null) {
                leafNodes.remove(currentNode);
            }
            leafNodes.add(newNode);
            nodeNum--;
        }
        return root;
    }

    public static List<Integer> getRandomList(int num) {
        List<Integer> list = new ArrayList<>(num);
        while (num > 0) {
            list.add(random.nextInt());
            num--;
        }
        return list;
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            sb.append((char)(32 + random.nextInt(95)));
            length--;
        }
        return sb.toString();
    }
}
