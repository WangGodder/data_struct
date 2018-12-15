package chapter5;

public class TrebleTuple<T1, T2, T3> {
    private T1 one;
    private T2 two;
    private T3 three;

    public TrebleTuple() {}

    public TrebleTuple(T1 one, T2 two, T3 three) {
        this.one = one;
        this.two = two;
        this.three = three;
    }

    public T1 getOne() {
        return one;
    }

    public T2 getTwo() {
        return two;
    }

    public T3 getThree() {
        return three;
    }

    public void setOne(T1 one) {
        this.one = one;
    }

    public void setTwo(T2 two) {
        this.two = two;
    }

    public void setThree(T3 three) {
        this.three = three;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        try {
            sb.append(one.toString());
            sb.append(" ");
            i++;
            sb.append(two.toString());
            sb.append(" ");
            i++;
            sb.append(three.toString());
        } catch (NullPointerException e) {
            System.out.println("第" + i + "个元素为空\n");
            e.printStackTrace();
        }

        return sb.toString();
    }
}
