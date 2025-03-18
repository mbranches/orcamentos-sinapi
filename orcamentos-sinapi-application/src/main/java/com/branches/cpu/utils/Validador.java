package com.branches.cpu.utils;

public class Validador {
    public static boolean isValidNumber(String input) {
        return input.matches("-?\\d+(\\.\\d+)?");
    }
}
