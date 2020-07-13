package priv.just.framework.webflux.test;

import java.util.Observable;
import java.util.concurrent.CountDownLatch;

public class ObserverDemo {

    public static void main(String[] args) throws InterruptedException {
        Publisher publisher = new Publisher(new int[] {1, 2, 3});
        publisher.addObserver((o, data) -> {
            System.out.println(data);
        });
        publisher.notifyObservers();
        new CountDownLatch(1).await();
    }

    private static class Publisher extends Observable {

        private final int[] arr;

        public Publisher(int[] arr) {
            this.arr = arr;
        }

        @Override
        public void notifyObservers() {
            for (int number : arr) {
                setChanged();
                super.notifyObservers(number);
            }
        }

    }

}
