import java.util.LinkedList;
import java.util.Queue;

public class PracticeAndConsumer {

    public static void main(String[] args) {

        Queue<Integer> productQueue = new LinkedList<>();

        Producer producer = new Producer(productQueue);
        Thread thread1 = new Thread(producer, "produce1");
        Thread thread2 = new Thread(producer, "produce2");
        Thread thread3 = new Thread(producer, "produce3");
        Thread thread4 = new Thread(producer, "produce4");
        Thread thread5 = new Thread(producer, "produce5");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        Consumer consumer = new Consumer(productQueue);
        Thread thread6 = new Thread(consumer, "consumer6");
        Thread thread7 = new Thread(consumer, "consumer7");
        Thread thread8 = new Thread(consumer, "consumer8");
        Thread thread9 = new Thread(consumer, "consumer9");

        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
    }
}

class Producer implements Runnable{

    private final Queue queue;
    private volatile int count = 0;

    Producer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        synchronized (queue) {
                while (queue.size() >= 5) {
                    System.out.println("queue is full and producer stop produce");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count++;
                queue.add(count);
                System.out.println("我是生产者"+Thread.currentThread().getName()+"我向队列里面放了一个产品了"+count);
                queue.notify();
        }
    }
}

class Consumer implements Runnable {

    private final Queue queue;
    private volatile int count;

    Consumer(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        synchronized (queue) {
                while (queue.isEmpty()) {
                    System.out.println("queue is empty and consumer stop to consume");
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int product = (int) queue.poll();
                System.out.println("我是消费者"+Thread.currentThread().getName()+"我取走了产品"+product);
                queue.notify();
        }
    }
}
