package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class OneTimeTask extends Task{
    public OneTimeTask(String title, Type type, String description, LocalDateTime localDateTime) {

        super(title, type, description ,localDateTime);
    }
    public boolean isValidNow(){
        return getDate().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isTodaysTask() {
        return (getDate().getYear() == LocalDate.now().getYear() &&
                getDate().getDayOfYear() == LocalDate.now().getDayOfYear() &&
                getDate().toLocalTime().isAfter(LocalTime.now()) &&
                getDate().isAfter(LocalDateTime.now()));
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return getDate().toLocalDate().isEqual(localDate);
    }
    @Override
    public LocalDateTime getNextDate() {
        if (LocalDateTime.now().isAfter(getDate())) return null;
        return getDate();
    }
    @Override
    public String toString() {
        return "Onetime task: " + super.toString();
    }
}
