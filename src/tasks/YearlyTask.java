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
    public LocalDateTime setDATE(String hours, String minutes, String seconds) {
        return LocalDateTime.now();
    }
    @Override
    public boolean isTodaysTask() {
        int leapYear = getDATE().getMonth().equals(Month.FEBRUARY) && getDATE().getDayOfMonth() == 29 ? 1 : 0;
        return (getDATE().getDayOfMonth() == LocalDate.now().getDayOfMonth() + leapYear && getDATE().getMonth() == LocalDate.now().getMonth()) && getDATE().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return localDate.isAfter(getDATE().toLocalDate()) && localDate.getDayOfYear() == getDATE().getDayOfYear();
    }
    public LocalDateTime getNextDate() {
        LocalDateTime temp = getDATE();
        while(getDATE().isBefore(LocalDateTime.now())){
            temp = temp.plusYears(1);
        }
        return temp;
    }
    @Override
    public String toString() {
        return "Yearly task: " + super.toString();
    }
}
