package priv.just.framework.demo.test;

public class ThreadTest {

    private volatile String flag = "A";

    public void invoke() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < 3; i++) {
                if ("A".equals(flag)) {
                    flag = "B";
                    System.out.println("A");
                    notifyAll();
                    wait();
                } else if ("B".equals(flag)) {
                    flag = "C";
                    System.out.println("B");
                    notifyAll();
                    wait();
                } else if ("C".equals(flag)) {
                    flag = "A";
                    System.out.println("C");
                    notifyAll();
                    wait();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        new Thread(() -> {
            try {
                threadTest.invoke();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                threadTest.invoke();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                threadTest.invoke();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(10000);
    }

}
