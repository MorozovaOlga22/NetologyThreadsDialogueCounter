package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static final int MAX_THREADS_COUNT = 5;
    private static final int TIME_TO_SLEEP = 1_000;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final Random random = new Random();
        final int threadsCount = random.nextInt(MAX_THREADS_COUNT) + 1;

        final ExecutorService threadPool = Executors.newFixedThreadPool(threadsCount);
        System.out.println("Ждем результат выполнения всех задач");
        printAllTasksResult(threadsCount, threadPool);
        System.out.println();
        System.out.println("Ждем результат выполнения одной задачи");
        printAnyTasksResult(threadsCount, threadPool);

        threadPool.shutdown();
    }

    private static void printAllTasksResult(int threadsCount, ExecutorService threadPool) throws InterruptedException {
        final List<MyCallable> tasks = getCallables(threadsCount);

        final List<Future<Integer>> futures = threadPool.invokeAll(tasks);
        while (!futures.stream().allMatch(Future::isDone)) {
            Thread.sleep(TIME_TO_SLEEP);
        }

        futures.forEach(future -> {
            try {
                System.out.println("Количество сообщений: " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static List<MyCallable> getCallables(int threadsCount) {
        final List<MyCallable> tasks = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            tasks.add(new MyCallable());
        }
        return tasks;
    }

    private static void printAnyTasksResult(int threadsCount, ExecutorService threadPool) throws ExecutionException, InterruptedException {
        final List<MyCallable> tasks = getCallables(threadsCount);

        final Integer messageCount = threadPool.invokeAny(tasks);
        System.out.println("Количество сообщений: " + messageCount);
    }
}
