package priv.just.framework.algorithm.sumoftwolist;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import priv.just.framework.algorithm.common.Node;

@Slf4j
public class FirstSolution implements Solution {

    @Override
    public Node invoke(Node l1, Node l2) {
        Node r1 = l1.reverse();
        Node r2 = l2.reverse();
        int extra = 0;
        Node p = new Node(0);
        Node head = p;
        while (r1 != null || r2 != null) {
            int val1 = r1 != null ? r1.val : 0;
            int val2 = r2 != null ? r2.val : 0;
            int val = val1 + val2 + extra;
            Node node;
            if (val >= 10) {
                node = new Node(val - 10);
                extra = 1;
            } else {
                node = new Node(val);
                extra = 0;
            }
            p.next = node;
            p = p.next;
            r1 = r1 != null ? r1.next : null;
            r2 = r2 != null ? r2.next : null;
        }
        return head.next.reverse();
    }

    @Test
    public void test() {
        Node l1 = Node.build(2, 4, 3, 4);
        Node l2 = Node.build(5, 6, 4, 6, 6);
        Node res = invoke(l1, l2);
        log.info(res.toString());
    }

}
