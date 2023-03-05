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
    public boolean isTodaysTask() {
        return (getDate().toLocalDate().isEqual(LocalDate.now()) && getDate().toLocalTime().isAfter(LocalTime.now())) || !(getDate().isAfter(LocalDateTime.now()));
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return !getDate().toLocalDate().isAfter(localDate);
    }
    @Override
    public LocalDateTime getNextDate() {
        LocalDateTime temp = LocalDateTime.of(LocalDate.now(), getDate().toLocalTime());
        if (getDate().toLocalTime().isAfter(LocalTime.now())) return getDate();
        return temp.plusDays(1);
    }
    @Override
    public String toString() {
        return "Daily task: " + super.toString();
    }
}
