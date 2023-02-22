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
        return getDATE().isAfter(LocalDateTime.now());
    }
    @Override
    public LocalDateTime setDATE(String hours, String minutes, String seconds) {
        return LocalDateTime.now();
    }
    @Override
    public boolean isTodaysTask() {
        return (getDATE().getYear() == LocalDate.now().getYear() &&
                getDATE().getDayOfYear() == LocalDate.now().getDayOfYear() &&
                getDATE().toLocalTime().isAfter(LocalTime.now()) &&
                getDATE().isAfter(LocalDateTime.now()));
    }
    @Override
    public boolean isExactlyDayTask(LocalDate localDate) {
        return getDATE().toLocalDate().isEqual(localDate);
    }
    @Override
    public LocalDateTime getNextDate() {
        if (LocalDateTime.now().isAfter(getDATE())) return null;
        return getDATE();
    }
    @Override
    public String toString() {
        return "Onetime task: " + super.toString();
    }
}
