package com.github.msemitkin;

import parcs.AM;
import parcs.AMInfo;

import java.util.stream.LongStream;

public class ArmstrongNumberCounter implements AM {

    public void run(AMInfo info) {
        System.out.println("Worker started");

        long start = info.parent.readLong();
        System.out.println("range start = " + start);
        long end = info.parent.readLong();
        System.out.println("range end = " + end);

        ArmstrongNumberChecker armstrongNumberChecker = new ArmstrongNumberChecker();

        System.out.println("counting Armstrong numbers in range [" + start + ", " + end + "]");
        long count = LongStream.rangeClosed(start, end)
            .filter(armstrongNumberChecker::isArmstrong)
            .count();
        System.out.println("writing count to parent");
        info.parent.write(count);
        System.out.println("Worker finished");
    }

}
