package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task{
    public MonthlyTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description ,localDateTime);
    }
    @Override
    public boolean isTodaysTask() {
        return getDate().getDayOfMonth() == LocalDate.now().getDayOfMonth() && getDate().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return localDate.isAfter(getDate().toLocalDate()) && getDate().getDayOfMonth() == localDate.getDayOfMonth();
    }
    @Override
    public LocalDateTime getNextDate() {
        LocalDateTime temp = LocalDateTime.now().withMonth(getDate().getMonthValue());
        while(getDate().isBefore(LocalDateTime.now())){
            temp = temp.plusMonths(1);
        }
        return temp;

    }
    @Override
    public String toString() {
        return "Monthly task: " + super.toString();
    }
}
