package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task{
    public WeeklyTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description, localDateTime);
    }
    @Override
    public LocalDateTime setDATE(String hours, String minutes, String seconds) {
        return LocalDateTime.now();
    }
    @Override
    public boolean isTodaysTask() {
        return getDATE().getDayOfWeek() == LocalDate.now().getDayOfWeek() && getDATE().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return localDate.isAfter(getDATE().toLocalDate()) && localDate.getDayOfWeek() == getDATE().getDayOfWeek();
    }
    @Override
    public LocalDateTime getNextDate() {
        LocalDateTime temp = LocalDateTime.now().withDayOfMonth(getDATE().getDayOfMonth());
        while(getDATE().isBefore(LocalDateTime.now())){
            temp = temp.plusDays(7);
        }
        return temp;
    }
    @Override
    public String toString() {
        return "Weekly task: " + super.toString();
    }
}
