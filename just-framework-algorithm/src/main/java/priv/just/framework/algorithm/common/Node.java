package priv.just.framework.algorithm.common;

public class Node {

    public int val;

    public Node next;

    public Node(int val) {
        this.val = val;
    }

    public Node reverse() {
        Node pre = null;
        Node cur = this;
        while (cur != null) {
            Node oNext = cur.next;
            cur.next = pre;
            pre = cur;
            cur = oNext;
        }
        return pre;
    }

    public static Node build(int... values) {
        if (values == null || values.length == 1) {
            return null;
        }
        Node head = new Node(values[0]);
        Node p = head;
        for (int i = 1; i < values.length; i++) {
            Node next = new Node(values[i]);
            p.next = next;
            p = next;
        }
        return head;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.val);
        Node p = next;
        while (p != null) {
            builder.append(" -> ").append(p.val);
            p = p.next;
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(build(1, 2, 7, 9, 3).reverse());
    }

}
