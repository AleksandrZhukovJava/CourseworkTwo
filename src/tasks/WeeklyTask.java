package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task{
    public WeeklyTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description, localDateTime);
    }
    @Override
    public boolean isTodaysTask() {
        return getDate().getDayOfWeek() == LocalDate.now().getDayOfWeek() && getDate().isBefore(LocalDateTime.now());
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return localDate.isAfter(getDate().toLocalDate()) && localDate.getDayOfWeek() == getDate().getDayOfWeek();
    }
    @Override
    public LocalDateTime getNextDate() {
        LocalDateTime temp = LocalDateTime.now().withDayOfMonth(getDate().getDayOfMonth());
        while(getDate().isBefore(LocalDateTime.now())){
            temp = temp.plusDays(7);
        }
        return temp;
    }
    @Override
    public String toString() {
        return "Weekly task: " + super.toString();
    }
}
