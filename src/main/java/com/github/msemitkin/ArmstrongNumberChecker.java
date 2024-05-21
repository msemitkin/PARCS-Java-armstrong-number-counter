package com.github.msemitkin;

public class ArmstrongNumberChecker {

    public boolean isArmstrong(long num) {
        long sum = 0;
        long temp = num;
        int numberOfDigits = String.valueOf(num).length();

        while (temp != 0) {
            long digit = temp % 10;
            sum += (long) Math.pow(digit, numberOfDigits);
            temp /= 10;
        }

        return sum == num;
    }

}
