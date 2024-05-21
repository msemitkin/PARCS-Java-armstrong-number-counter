package com.github.msemitkin;

import org.apache.commons.lang3.time.StopWatch;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;
import parcs.task;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String jarPath = args[0];
        System.out.println("jarPath = " + jarPath);
        int numberOfWorkers = Integer.parseInt(args[1]);
        System.out.println("numberOfWorkers = " + numberOfWorkers);
        long range = Long.parseLong(args[2]);
        System.out.println("range = " + range);

        task task = new task();
        task.addJarFile(jarPath);

        StopWatch stopwatch = StopWatch.createStarted();

        long totalCount = getTotalCount(new AMInfo(task, null), range, numberOfWorkers);

        stopwatch.stop();

        System.out.println("Number of Armstrong numbers in range [1, " + range + "] = " + totalCount);
        System.out.println("duration = " + stopwatch);
        task.end();
    }

    private static long getTotalCount(AMInfo info, long range, int numberOfWorkers) {
        List<channel> channels = new ArrayList<>();

        long batchSize = range / numberOfWorkers;

        System.out.println("batchSize = " + batchSize);
        for (int index = 0; index < numberOfWorkers; ++index) {
            long rangeStart = index * batchSize + 1;
            long rangeEnd = (index + 1) * batchSize;

            System.out.println("creating point for range [" + rangeStart + ", " + rangeEnd + "]");
            point point = info.createPoint();
            System.out.println("creating channel");
            channel channel = point.createChannel();
            channels.add(channel);

            System.out.println("executing point");
            point.execute(ArmstrongNumberCounter.class.getCanonicalName());

            System.out.println("writing to channel");
            channel.write(rangeStart);
            channel.write(rangeEnd);
        }

        System.out.println("waiting for results");
        return channels.stream()
            .map(channel::readLong)
            .mapToLong(Long::longValue)
            .sum();
    }
}
