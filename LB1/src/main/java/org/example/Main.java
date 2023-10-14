package org.example;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        double a = 1;
        double b = 9;
        int n = 100_000_000;


        int nThreads = 10;
        double delta = (b - a) / nThreads;
        totalResult = 0;
        finished = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            RunnableIntegralCalculator calculator = new RunnableIntegralCalculator(a + i * delta, a + i * delta + delta, n / nThreads, this::fun, this);
            new Thread(calculator).start();
        }
        try {
            synchronized (this) {
                while (finished < nThreads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted");
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("Result = " + totalResult);
        System.out.println(finishTime - startTime);
    }

    public synchronized void send(double v) {
        totalResult += v;
        finished++;
        notify();
    }

    private double totalResult;
    private int finished;

    public double fun(double x){
      return 3 * Math.sqrt(x)*(1 + Math.sqrt(x));

    }
}
