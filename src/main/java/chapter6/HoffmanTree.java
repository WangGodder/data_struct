package chapter6;

import java.util.*;

/**
 * @author: godder
 * @date: 2018/12/3
 */
public class HoffmanTree {

    /**
     * use hoffman tree to realize hoffman code of a string
     * @param str   the string to code
     * @param codeSize  the number of chars to code the string
     * @param chars the chars to code
     * @return  a map of char in string and code char
     */
    public static HashMap<Character, String> code(String str, int codeSize, char... chars) {
        if (chars.length != 0 && codeSize != chars.length) {
            throw new RuntimeException("code size is not match chars");
        }
        HashMap<Character, Integer> staisticMap = statisticChar(str);
        Map.Entry<Character, Integer>[] entries = new Map.Entry[staisticMap.size()];
        staisticMap.entrySet().toArray(entries);
        Node root = createTree(entries, codeSize);
        return encodeHuffman(root, chars);
    }

    /**
     * statistics the frequency of each char in string
     * @param str
     * @return
     */
    public static HashMap<Character, Integer> statisticChar(String str) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (Character c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0)+1);
        }
        return map;
    }

    /**
     * generate hoffman tree base on frequency
     * @param entries the map of char and weight
     * @param treeSize the number of children of each node
     * @return
     */
    public static Node createTree(Map.Entry<Character, Integer>[] entries, int treeSize) {
        Arrays.sort(entries, new Comparator<Map.Entry<Character, Integer>>() {
            @Override
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return Integer.compare(o1.getValue(), o2.getValue());
            }
        });
        // 利用list存储node，按照权重排序
        LinkedList<Tuple<Node, Integer>> nodeList = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : entries) {
            nodeList.add(new Tuple<Node, Integer>(new Node(entry.getKey(), treeSize), entry.getValue()));
        }
        // 遍历累加，将新生成得点加入list，直到list的长度==1
        Node root = null;
        int listSize = nodeList.size();
        while ((listSize = nodeList.size()) > 1) {
            Node newNode = new Node('\0', treeSize);
            int sum = 0;
            // 遍历剩下的node中的treeSize个，最后一次可能不够treeSize个
            for (int i = 0; i < Math.min(treeSize, listSize); i++) {
                newNode.children[i] = nodeList.getFirst().value1;
                sum += nodeList.getFirst().value2;
                nodeList.removeFirst();
            }
            insert(nodeList, newNode, sum);
            root = newNode;
        }
        return root;
    }

    /**
     * encode huffman tree to transform each char in string to a new unique base char rank
     * @param root  the root of hoffman tree
     * @param representChars    the char set to encode if null use 0 to 9
     * @return
     */
    public static HashMap<Character, String> encodeHuffman(Node root, char... representChars) {
        boolean selfChars = representChars.length != 0;
        if (root == null) {
            throw new NullPointerException("root can not be Null");
        }
        if (root.childrenNum > 9 && selfChars) {
            throw new RuntimeException("encode use more than 9 base char, please input char set to make sure expression unique");
        }
        HashMap<Character, String> map = new HashMap<>();
        Node current = root;
        if (selfChars) {
            preorderTraverseSelf(root, new StringBuilder(), map, representChars);
        } else {
            preorderTraverse(root, new StringBuilder(), map);
        }
        return map;
    }

    private static void insert(LinkedList<Tuple<Node, Integer>> list, Node node, int sum) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).value2 > sum) {
                list.add(i, new Tuple<>(node, sum));
                break;
            }
        }
        if (size == list.size())  {
            list.addLast(new Tuple<>(node, sum));
        }
    }

    private static void preorderTraverse(Node node, StringBuilder code, HashMap<Character, String> map) {
        if (node == null) {
            return;
        }
        if (node.children[0] == null) {
            map.put(node.value, code.toString());
            return;
        }
        for (int i = 0; i < node.childrenNum; i++) {
            preorderTraverse(node.children[i], new StringBuilder(code).append(i), map);
        }
    }

    private static void preorderTraverseSelf(Node node, StringBuilder code, HashMap<Character, String> map, char[] chars) {
        if (node == null) {
            return;
        }
        if (node.children[0] == null) {
            map.put(node.value, code.toString());
            return;
        }
        for (int i = 0; i < node.childrenNum; i++) {
            preorderTraverse(node.children[i], new StringBuilder(code).append(chars[i]), map);
        }
    }


    static class Node {
        char value;
        Node[] children;
        int childrenNum;

        public Node(char value, int childrenNum) {
            this.value = value;
            this.childrenNum = childrenNum;
            this.children = new Node[this.childrenNum];
        }
    }


    static class Tuple<T1, T2> {
        T1 value1;
        T2 value2;

        public Tuple(T1 value1, T2 value2) {
            this.value1 = value1;
            this.value2 = value2;
        }
    }
}
