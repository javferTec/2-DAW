package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {

    private static List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9,1);
    private static List<String> strings = Arrays.asList("uno", "dos", "tres");

    public static void main(String[] args) {
        ejercicio1();
        ejercicio2();
        ejercicio3();
        ejercicio4();
        ejercicio5();
        ejercicio6();
        ejercicio7();
        ejercicio8();
        ejercicio9();
        ejercicio10();
    }

    public static Integer ejercicio1() {
        Integer suma = integers.stream().reduce(0, Integer::sum);
        System.out.println("Ejercicio 1: " + suma);
        return suma;
    }

    public static List<Integer> ejercicio2() {
        List<Integer> pares = integers.stream().filter(i -> i % 2 == 0).toList();
        System.out.println("Ejercicio 2: " + pares);
        return pares;
    }

    public static List<String> ejercicio3() {
        List<String> stringsMayusculas = strings.stream().map(String::toUpperCase).toList();
        System.out.println("Ejercicio 3: " + stringsMayusculas);
        return stringsMayusculas;
    }

    public static Integer ejercicio4() {
        Integer maxNumber = integers.stream().max(Integer::compare).get();
        System.out.println("Ejercicio 4: " + maxNumber);
        return maxNumber;
    }

    public static Integer ejercicio5() {
        int numberToCompare = 5;
        long count = integers.stream().filter(i -> i > numberToCompare).count();
        System.out.println("Ejercicio 5: " + count);
        return (int) count;
    }

    public static String ejercicio6() {
        String convatenatedString = strings.stream().reduce("", (a, b) -> a + b);
        System.out.println("Ejercicio 6: " + convatenatedString);
        return convatenatedString;
    }

    public static List<Integer> ejercicio7() {
        List<Integer> elevatedNumbers = integers.stream().map(i -> i * i).toList();
        System.out.println("Ejercicio 7: " + elevatedNumbers);
        return elevatedNumbers;
    }

    public static List<Integer> ejercicio8() {
        List<Integer> nonDuplicatedNumbers = integers.stream().distinct().toList();
        System.out.println("Ejercicio 8: " + nonDuplicatedNumbers);
        return nonDuplicatedNumbers;
    }

    public static Integer ejercicio9() {
        Integer numberToCompare = 5;
        Integer firstNumberGreaterThan = integers.stream().filter(i -> i > numberToCompare).findFirst().get();
        System.out.println("Ejercicio 9: " + firstNumberGreaterThan);
        return firstNumberGreaterThan;
    }

    public static Integer ejercicio10() {
        String letterToCount = "u";
        long count = strings.stream().flatMapToInt(String::chars).filter(c -> c == letterToCount.charAt(0)).count();
        System.out.println("Ejercicio 10: " + count);
        return (int) count;
    }

}