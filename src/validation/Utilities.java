package validation;

import java.time.*;
import java.util.Scanner;

public class Utilities {
    public static void separatedMessage(String message, boolean indentF,boolean indentL) {
        if (indentF) System.out.println();
        System.out.println("-".repeat(message.length()) + "\n" + message + "\n" + "-".repeat(message.length()));
        if (indentL) System.out.println();
    }
    public static LocalDateTime setLocalDate(boolean time) {
        int year = amountValidation("Set year", LocalDateTime.now().getYear(), LocalDateTime.now().getYear() + 100);
        boolean isThisYear = year == LocalDateTime.now().getYear();
        int month = amountValidation("Set month", isThisYear ? LocalDateTime.now().getMonth().getValue() : 1, 12);
        boolean isThisMonth = month == LocalDateTime.now().getMonth().getValue();
        int day = amountValidation("Set day", isThisYear && isThisMonth ? LocalDateTime.now().getDayOfMonth() : 1, Month.of(month).length(Year.isLeap(year)));
        if (time) {
            boolean isThisDay = day == LocalDate.now().getDayOfMonth();
            int hour = amountValidation("Set hour", isThisYear && isThisMonth && isThisDay ? LocalDateTime.now().getHour() : 0, 24);
            boolean isThisHour = hour == LocalDateTime.now().getHour();
            int minutes = amountValidation("Set minutes", isThisYear && isThisMonth && isThisDay && isThisHour ? LocalDateTime.now().getMinute() : 0, 60);
            return LocalDateTime.of(year, month, day, hour, minutes, 0);
        } else {
            return LocalDateTime.of(year, month, day, 0, 0, 0);
        }
    }
    public static int amountValidation(String message, int min, int max) {
        Scanner sc1 = new Scanner(System.in);
        System.out.println(message);
        boolean result = false;
        int temp;
        String tempString;
        do {
            do {
                tempString = sc1.nextLine();
                if (!tempString.matches("\\d+")) {
                    System.err.println("Invalid number");
                }
            } while (!tempString.matches("\\d+"));
            temp = Integer.parseInt(tempString);
            if (temp >= min && temp <= max) result = true;
            else System.err.println("Invalid number");
        } while (!result);
        return temp;
    }
}
