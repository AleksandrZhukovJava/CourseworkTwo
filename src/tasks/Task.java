package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Task implements Comparable<Task>{
    protected static int idGenerator = 1;
    private String title;
    private final Type TYPE;
    private String description;
    private final int ID;
    private final LocalDateTime DATE;
    public Task(String title, Type type, String description, LocalDateTime localDateTime) {
        this.title = title;
        this.TYPE = type;
        this.description = description;
        DATE = localDateTime;
        ID = idGenerator;
        idGenerator++;
    }
    public abstract LocalDateTime getNextDate();
    public abstract boolean isTodaysTask();
    public abstract boolean isExactlyDayTask(LocalDate localDate);
    public boolean isValidNow(){return true;}
    public abstract LocalDateTime setDATE(String a, String b, String c);
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public Type getType() {
        return TYPE;
    }
    public int getID() {
        return ID;
    }
    public LocalDateTime getDATE() {return DATE;}
    public LocalDate getLocalDate() {return DATE.toLocalDate();}
    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd" + "." + "MM" + "." + "yyyy" + "г. " + "H" + "ч." + "m" + "мин");
        String discriptionToString = description.isBlank() ? "." : ", description - " + description;
        return title + ", type - " + TYPE.getType() + ", id - " + ID + ", date of creating - " + DATE.format(dateTimeFormatter) + discriptionToString;
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public int compareTo(Task o) {
        if (this.getDATE().isAfter(o.getDATE()))
            return 1;
        else if (this.getDATE().isBefore(o.getDATE()))
            return -1;
        return 0;
    }
}
