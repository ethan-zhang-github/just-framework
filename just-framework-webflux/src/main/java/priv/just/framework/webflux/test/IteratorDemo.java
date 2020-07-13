package priv.just.framework.webflux.test;

import java.util.Iterator;

public class IteratorDemo {

    public static void main(String[] args) {
        Publisher publisher = new Publisher(new int[] {1, 2, 3});
        Publisher.PublisherIterator iterator = publisher.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static class Publisher {

        private final int[] arr;

        public Publisher(int[] arr) {
            this.arr = arr;
        }

        public PublisherIterator iterator() {
            return new PublisherIterator();
        }

        private class PublisherIterator implements Iterator<Integer> {

            private int index;

            @Override
            public boolean hasNext() {
                return index < arr.length;
            }

            @Override
            public Integer next() {
                int res = arr[index];
                index++;
                return res;
            }

        }

    }

}
