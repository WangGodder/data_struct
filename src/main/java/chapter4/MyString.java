package chapter4;

/**
 * author: godder
 */

public class MyString {
    private char[] chars;

    public MyString() {
        chars = new char[]{'\0'};
    }

    public MyString(char[] array) {
        if (array[array.length - 1] != '\0') {
            chars = new char[array.length + 1];
            chars[array.length] = '\0';
        } else {
            chars = new char[array.length];
        }
        System.arraycopy(array, 0, chars, 0, array.length);
    }

    public int length() {
        return chars.length - 1;
    }

    public int size() {
        return chars.length;
    }

    public char get(int index) {
        return chars[index];
    }

    public void strconcat(MyString str) {
        char[] newChars = new char[this.size() + str.length()];
        System.arraycopy(chars, 0, newChars, 0, this.length());
        int i = 0;
        for (; i < str.length(); i++) {
            newChars[this.length()+i] = str.get(i);
        }
        newChars[i] = '\0';
        this.chars = newChars;
    }

    public MyString strcopy() {
        MyString copy = new MyString(chars);
        return copy;
    }

    public int strcmp(MyString str) {
        if (this.size() == str.size()) {
            for (int i = 0; i < chars.length - 1; i++) {
                int compare = chars[i] - str.get(i);
                if (compare != 0)
                    return compare;
            }
        }
        return this.size() - str.size();
    }

    public boolean insert(int index, char[] insertChars) {
        if (index > this.length()) {
            System.err.println("index 超过字符串长度");
            return false;
        }
        int insertLength = insertChars.length;
        if (insertChars[insertLength - 1] == '\0')
            insertLength--;
        char[] newChars = new char[this.size() + insertLength];
        System.arraycopy(chars, 0, newChars, 0, index);
        System.arraycopy(insertChars, 0, newChars, index, insertLength);
        System.arraycopy(chars, index, newChars, index + insertLength, this.size() - index);
        this.chars = newChars;
        return true;
    }

    public boolean strreplace(MyString partten, MyString target) {
        int[] next = new int[this.length()];
        next[0] = -1;
        int j = 0, k = -1, n = 0, m = 0;
        while (j < next.length - 1) {
            if (k == -1 || chars[j] == chars[k]) {
                next[++j] = ++k;
            } else
                k = next[k];
        }
        while (n < chars.length - 1 && m < partten.length()) {
            if (m == -1 || chars[n] == partten.get(n)) {
                n++;    m++;
            } else
                m = next[m];
        }

        if (m != partten.length())
            return false;
        int index = n - m;
        char[] newChars = new char[this.size() + target.length() - partten.length()];
        System.arraycopy(chars, 0, newChars, 0, index);
        int i = index;
        for (; i < partten.length() + index; i++) {
            newChars[i] = partten.get(i - index);
        }
        System.arraycopy(chars, 0, newChars, i, chars.length - index);
        return true;
    }

    public MyString substr(int begin, int end) {
        char[] newChars = new char[end - begin + 1];
        System.arraycopy(chars, begin, newChars, 0, end - begin + 1);
        return new MyString(newChars);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(chars);
        return sb.toString();
    }
}
