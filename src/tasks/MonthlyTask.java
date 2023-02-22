package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task{
    public MonthlyTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description ,localDateTime);
    }
    @Override
    public LocalDateTime setDATE(String hours, String minutes, String seconds) {
        return LocalDateTime.now();
    }
    @Override
    public boolean isTodaysTask() {
        return getDATE().getDayOfMonth() == LocalDate.now().getDayOfMonth() && getDATE().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return localDate.isAfter(getDATE().toLocalDate()) && getDATE().getDayOfMonth() == localDate.getDayOfMonth();
    }
    @Override
    public LocalDateTime getNextDate() {
        LocalDateTime temp = LocalDateTime.now().withMonth(getDATE().getMonthValue());
        while(getDATE().isBefore(LocalDateTime.now())){
            temp = temp.plusMonths(1);
        }
        return temp;

    }
    @Override
    public String toString() {
        return "Monthly task: " + super.toString();
    }
}
