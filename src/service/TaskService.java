package service;

import exceptions.TaskNotFoundException;
import tasks.*;
import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static service.Serialization.save;
import static type.Type.*;
import static validation.Utilities.*;

public class TaskService {
    public static final List<Task> taskList = new ArrayList<>();
    public static final List<Task> removedTasks = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    private static boolean exit = false;
    private static final DateTimeFormatter mainDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyyг. HHч.mmм.");
    public static void main(String[] args) {
        menu();
    }
    public static void menu() {
        while (!exit) {
            refreshList();
            switch (amountValidation("""
                                  Choose option:
                    1) Create task             6) Print today`s tasks
                    2) Change task             7) Print tasks on day
                    3) Delete task by id       8) Print all tasks
                    4) Next date of task       9) Print tasks group by dates
                    5) Exit                    10) Print removed tasks
                    """, 1, 10)) {
                case 1 -> add();
                case 2 -> changeTask(amountValidation("Set ID for changing", 0, taskList.size() + removedTasks.size()));
                case 3 -> remove(amountValidation("Set ID for removing", 0, taskList.size() + removedTasks.size()));
                case 4 -> nextDateOfTask(amountValidation("Set ID of task", 0, taskList.size() + removedTasks.size()));
                case 5 -> exit = true;
                case 6 -> getAllTasksForToday();
                case 7 -> getAllTasksForDay(setLocalDate(false).toLocalDate());
                case 8 -> printAllTasks();
                case 9 -> printTasksGroupByDate();
                case 10 -> printRemovedTasks();
                default -> {
                }
            }
        }
        System.out.println("GoodBuy");
        scanner.close();
    }
    private static void add() {
        String title;
        do {
            System.out.println("Set title:");
            title = scanner.nextLine();
        } while (title.isBlank());

        Type taskType = null;
        int type = amountValidation("Select type:\n1) Work\n2) Personal", 1, 2);
        switch (type) {
            case 1 -> taskType = WORK;
            case 2 -> taskType = PERSONAL;
            default -> {
            }
        }

        String description;
        System.out.println("Set description");
        description = scanner.nextLine();

        Task newTask = null;
        switch (amountValidation("""
                \nHow often need to repeat this task?
                1)Don`t need to repeat
                2)Everyday
                3)Every week
                4)Every month
                5)Every year""", 1, 5)) {
            case 1 -> newTask = new OneTimeTask(title, taskType, description, setLocalDate(true));
            case 2 -> newTask = new DailyTask(title, taskType, description, setLocalDate(true));
            case 3 -> newTask = new WeeklyTask(title, taskType, description, setLocalDate(true));
            case 4 -> newTask = new MonthlyTask(title, taskType, description, setLocalDate(true));
            case 5 -> newTask = new YearlyTask(title, taskType, description, setLocalDate(true));
            default -> {
            }
        }
        taskList.add(newTask);
        System.out.println("Task created\n");
    }
    private static void remove(int id) {
        try {
            Task temp = taskList.stream().filter(task -> task.getId() == id).findFirst().orElseThrow(TaskNotFoundException::new);
            taskList.remove(temp);
            removedTasks.add(temp);
            System.out.println("\nTask id - " + id + " was successfully removed\n");
        } catch (TaskNotFoundException e) {
            System.err.println("\nTask not found\n");
        }
    }
    private static void changeTask(int id) {
        try {
            Task temp = taskList.stream().filter(x -> x.getId() == id).findFirst().orElseThrow(TaskNotFoundException::new);
            int choiceInt = amountValidation("""
                    Change:
                    1) Title
                    2) Discription""", 1, 2);
            switch (choiceInt) {
                case 1 -> {
                    String tempTitle;
                    do {
                        System.out.println("Set title:");
                        tempTitle = scanner.nextLine();
                        temp.setTitle(tempTitle);
                    } while (tempTitle.isBlank());
                }
                case 2 -> {
                    System.out.println("Set description:");
                    temp.setDescription(scanner.nextLine());
                }
                default -> {
                }
            }
            System.out.println("Your task was successfully changed!\n");
        } catch (TaskNotFoundException e) {
            System.err.println("\nTask not found\n");
        }
    }
    private static void nextDateOfTask(int id) {
        try {
            Task temp = taskList.stream().filter(task -> task.getId() == id).findFirst().orElseThrow(TaskNotFoundException::new);
            System.out.println("\nNext time of this task is " + temp.getNextDate().format(mainDateTimeFormatter) + "\n");
        } catch (TaskNotFoundException e) {
            System.err.println("\nTask not found\n");
        }
    }
    private static void getAllTasksForToday() {
        if (taskList.stream().noneMatch(Task::isTodaysTask)) {
            separatedMessage("No tasks for today", true, true);
        } else {
            separatedMessage("Tasks for today", true, false);
            taskList.stream().filter(Task::isTodaysTask).forEach(System.out::println);
            System.out.print("\n");
        }
    }
    private static void getAllTasksForDay(LocalDate localDate) {
        if (taskList.stream().noneMatch(x -> x.isExactlyDayTask(localDate))) {
            separatedMessage("No tasks for that day", true, true);
        } else {
            separatedMessage("Tasks for that day", true, false);
            taskList.stream().filter(x -> x.isExactlyDayTask(localDate)).forEach(System.out::println);
            System.out.print("\n");
        }
    }
    private static void printAllTasks() {
        if (taskList.isEmpty()) {
            separatedMessage("You dont have any task", true, true);
        } else {
            separatedMessage("All tasks", true, false);
            taskList.forEach(System.out::println);
            System.out.print("\n");
        }
    }
    private static void printTasksGroupByDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd" + "." + "MM" + "." + "yyyy" + "г.");
        for (var pair : taskList.stream().collect(Collectors.groupingBy(x -> x.getDate().toLocalDate(), Collectors.toList())).entrySet()) {
            separatedMessage(pair.getKey().format(dateTimeFormatter), false, false);
            pair.getValue().forEach(System.out::println);
        }
        System.out.println();
    }
    private static void printRemovedTasks() {
        if (removedTasks.isEmpty()) {
            separatedMessage("You dont have removed tasks", true, true);
        } else {
            separatedMessage("Removed tasks", true, false);
            for (var task : removedTasks) {
                System.out.println(task);
            }
            System.out.print("\n");
        }
    }
    private static void refreshList() {
        List<Task> temp = taskList.stream().filter(task -> task.getDate().isBefore(LocalDateTime.now()) && task.isValidNow()).toList();
        taskList.removeAll(temp);
        removedTasks.addAll(temp);
    }
}
