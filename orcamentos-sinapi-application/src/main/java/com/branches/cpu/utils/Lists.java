package com.branches.cpu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Lists {
    public static <T> List<T> containsInList(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) result.add(t);
        }
        return result;
    }
}