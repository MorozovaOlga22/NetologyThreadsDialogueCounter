package ru.netology;

import java.util.Random;
import java.util.concurrent.Callable;

public class MyCallable implements Callable<Integer> {
    private static final int TIME_TO_SLEEP = 2_000;
    public static final int MAX_COUNT_TO_PRINT = 10;

    @Override
    public Integer call() throws Exception {
        final Random random = new Random();
        final int countToPrint = random.nextInt(MAX_COUNT_TO_PRINT);

        for (int i = 0; i < countToPrint; i++) {
            System.out.println("Я поток " + Thread.currentThread().getName() + ". Всем привет!");
            Thread.sleep(TIME_TO_SLEEP);
        }

        return countToPrint;
    }
}
