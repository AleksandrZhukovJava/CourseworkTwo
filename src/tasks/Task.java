package tasks;

import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static service.TaskService.removedTasks;
import static service.TaskService.taskList;

public abstract class Task implements Comparable<Task>{
    protected static int idGenerator = removedTasks.size() + taskList.size() + 1;
    private String title;
    private final Type type;
    private String description;
    private final int id;
    private final LocalDateTime date;
    public Task(String title, Type type, String description, LocalDateTime localDateTime) {
        this.title = title;
        this.type = type;
        this.description = description;
        date = localDateTime;
        id = idGenerator;
    }
    public abstract LocalDateTime getNextDate();
    public abstract boolean isTodaysTask();
    public abstract boolean isExactlyDayTask(LocalDate localDate);
    public boolean isValidNow(){return true;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public Type getType() {
        return type;
    }
    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {return date;}
    public String getDescription() {
        return description;
    }
    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd" + "." + "MM" + "." + "yyyy" + "г. " + "H" + "ч." + "m" + "мин");
        String discriptionToString = description.isBlank() ? "." : ", description - " + description;
        return title + ", type - " + type.getType() + ", id - " + id + ", date of creating - " + date.format(dateTimeFormatter) + discriptionToString;
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
        if (this.getDate().isAfter(o.getDate()))
            return 1;
        else if (this.getDate().isBefore(o.getDate()))
            return -1;
        return 0;
    }
}
