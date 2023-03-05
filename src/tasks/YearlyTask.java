package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class YearlyTask extends Task{
    public YearlyTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description ,localDateTime);
    }
    @Override
    public boolean isTodaysTask() {
        int leapYear = getDate().getMonth().equals(Month.FEBRUARY) && getDate().getDayOfMonth() == 29 ? 1 : 0;
        return (getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth() + leapYear && getDate().getMonth() == LocalDate.now().getMonth()) && getDate().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return localDate.isAfter(getDate().toLocalDate()) && localDate.getDayOfYear() == getDate().getDayOfYear();
    }
    public LocalDateTime getNextDate() {
        LocalDateTime temp = getDate();
        while(getDate().isBefore(LocalDateTime.now())){
            temp = temp.plusYears(1);
        }
        return temp;
    }
    @Override
    public String toString() {
        return "Yearly task: " + super.toString();
    }
}
