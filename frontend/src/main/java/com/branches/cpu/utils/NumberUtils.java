package com.branches.cpu.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
    private static NumberFormat formatoBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String formatarValorBRL(double valor) {
        return formatoBr.format(valor);
    }

    public static boolean isValidNumber(String input) {
        return input.matches("-?\\d+(\\.\\d+)?");
    }
}
