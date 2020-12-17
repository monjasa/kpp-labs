package org.monjasa.lab04.util;

import lombok.*;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ThreadScannerTask extends TimerTask {

    private final List<Thread> threads;

    @Override
    public void run() {
        String delimiter = IntStream.range(0, 80).mapToObj(i -> "-").collect(Collectors.joining(""));
        System.out.println(delimiter);
        threads.forEach(thread -> System.out.printf("%s - %s, Alive: %s\n", thread, thread.getState(), thread.isAlive()));
        System.out.println(delimiter);
    }

    public void registerThread(Thread thread) {
        threads.add(thread);
    }
}
