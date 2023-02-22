package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DailyTask extends Task{
    public DailyTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description ,localDateTime);
    }
    @Override
    public LocalDateTime setDATE(String hours, String minutes, String seconds) {
        return LocalDateTime.now();
    }
    @Override
    public boolean isTodaysTask() {
        return (getDATE().toLocalDate().isEqual(LocalDate.now()) && getDATE().toLocalTime().isAfter(LocalTime.now())) || !(getDATE().isAfter(LocalDateTime.now()));
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return !getDATE().toLocalDate().isAfter(localDate);
    }
    @Override
    public LocalDateTime getNextDate() {
        LocalDateTime temp = LocalDateTime.of(LocalDate.now(), getDATE().toLocalTime());
        if (getDATE().toLocalTime().isAfter(LocalTime.now())) return temp;
        return temp.plusDays(1);
    }
    @Override
    public String toString() {
        return "Daily task: " + super.toString();
    }
}
