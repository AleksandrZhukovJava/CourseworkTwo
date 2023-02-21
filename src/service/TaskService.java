package service;

import exceptions.TaskNotFoundException;
import tasks.*;
import type.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static type.Type.*;
import static validation.Utilities.*;

public class TaskService {
    private static final List<Task>  taskList = new ArrayList<>();
    private static final List<Task> removedTasks = new ArrayList<>();
    public static Scanner scanner = new Scanner(System.in);
    private static boolean exit = false;
    private static final DateTimeFormatter mainDateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyyг. HHч.mmм.");
    public static void main(String[] args) {
        testWAR();
        while (!exit) {
            refreshList();
            menu();
        }
        System.out.println("GoodBuy");
    }
    public static void menu() {
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
            default -> {}
        }
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
            default -> {}
        }

        String description;
        System.out.println("Set description");
        description = scanner.nextLine();

        Task newTask = null ;
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
            default -> {}
        }
        taskList.add(newTask);
        System.out.println("Task created\n");
    }
    private static void remove(int id) {
        try {
            Task temp = taskList.stream().filter(task -> task.getID() == id).findFirst().orElseThrow(TaskNotFoundException::new);
            taskList.remove(temp);
            removedTasks.add(temp);
            System.out.println("\nTask id - " + id + " was successfully removed\n");
        } catch (TaskNotFoundException e) {
            System.err.println("\nTask not found\n");
        }
    }
    private static void changeTask(int id) {
        try {
            Task temp = taskList.stream().filter(x -> x.getID() == id).findFirst().orElseThrow(TaskNotFoundException::new);
            int choiceInt = amountValidation("""
                    Change:
                    1) Title
                    2) Discription""", 1, 2);
            switch (choiceInt) {
                case 1 -> {
                    do {
                        System.out.println("Set title:");
                        temp.setTitle(scanner.nextLine());
                    } while (taskList.get(id).getTitle().isBlank());
                }
                case 2 -> {
                    System.out.println("Set description:");
                    temp.setDescription(scanner.nextLine());
                }
                default -> {}
            }
            System.out.println("Your task was successfully changed!\n");
        } catch (TaskNotFoundException e) {
            System.err.println("\nTask not found\n");
        }
    }
    private static void nextDateOfTask(int id) {
        try {
            Task temp = taskList.stream().filter(task -> task.getID() == id).findFirst().orElseThrow(TaskNotFoundException::new);
            System.out.println("\nNext time of this task is " + temp.getNextDate().format(mainDateTimeFormatter) + "\n");
        } catch (TaskNotFoundException e) {
            System.err.println("\nTask not found\n");
        }
    }
    private static void getAllTasksForToday() {
        if (taskList.stream().noneMatch(Task::isTodaysTask)) {
            separatedMessage("No tasks for today", true,true);
        } else {
            separatedMessage("Tasks for today", true,false);
            taskList.stream().filter(Task::isTodaysTask).forEach(System.out::println);
            System.out.print("\n");
        }
    }
    private static void getAllTasksForDay(LocalDate localDate) {
        if (taskList.stream().noneMatch(x -> x.isExactlyDayTask(localDate))) {
            separatedMessage("No tasks for today", true,true);
        } else {
            separatedMessage("Tasks for today", true,false);
            taskList.stream().filter(x -> x.isExactlyDayTask(localDate)).forEach(System.out::println);
            System.out.print("\n");
        }
    }
    private static void printAllTasks() {
        if (taskList.isEmpty()) {
            separatedMessage("You dont have any task", true,true);
        } else {
            separatedMessage("All tasks", true,false);
            taskList.forEach(System.out::println);
            System.out.print("\n");
        }
    }
    private static void printTasksGroupByDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd" + "." + "MM" + "." + "yyyy" + "г.");
        for (var pair: taskList.stream().collect(Collectors.groupingBy(Task::getLocalDate,Collectors.toList())).entrySet()) {
            separatedMessage(pair.getKey().format(dateTimeFormatter),false,false);
            pair.getValue().forEach(System.out::println);
        }
        System.out.println();
    }
    private static void printRemovedTasks() {
        if (removedTasks.isEmpty()) {
            separatedMessage("You dont have removed tasks", true,true);
        } else {
            separatedMessage("Removed tasks", true,false);
            for (var task : removedTasks) {
                System.out.println(task);
            }
            System.out.print("\n");
        }
    }
    private static void refreshList() {
        List <Task> temp = taskList.stream().filter(task -> task.getDATE().isBefore(LocalDateTime.now()) && task.isValidNow()).toList();
        taskList.removeAll(temp);
        removedTasks.addAll(temp);
    }
    public static void testWAR(){
        DailyTask b = new DailyTask("Dayle treu",WORK,"1",LocalDateTime.of(2023,3,13,20,20));
        DailyTask b1 = new DailyTask("2",WORK,"2",LocalDateTime.of(2023,2,13,20,20));
        DailyTask b2 = new DailyTask("Dayle false",WORK,"3",LocalDateTime.of(2023,3,14,20,20));
        WeeklyTask b3 = new WeeklyTask("Week treu",WORK,"4",LocalDateTime.of(2023,3,6,20,20));
        WeeklyTask b4 = new WeeklyTask("Week false",WORK,"5",LocalDateTime.of(2023,3,7,20,20));
        WeeklyTask b5 = new WeeklyTask("Week false",WORK,"6",LocalDateTime.of(2023,3,14,20,20));
        MonthlyTask b6 = new MonthlyTask("Monthly treu",WORK,"7",LocalDateTime.of(2023,1,13,20,20));
        MonthlyTask b7 = new MonthlyTask("Monthly false",WORK,"8",LocalDateTime.of(2024,3,13,20,20));
        MonthlyTask b8 = new MonthlyTask("Monthly false",WORK,"9",LocalDateTime.of(2023,2,14,20,20));
        YearlyTask b9 = new YearlyTask("Yearly treu",WORK,"10",LocalDateTime.of(2022,3,13,20,20));
        YearlyTask b11 = new YearlyTask("Yearly false",WORK,"11",LocalDateTime.of(2023,3,3,20,20));
        OneTimeTask b12 = new OneTimeTask("Onetime treu",WORK,"12",LocalDateTime.of(2023,3,13,20,20));
        OneTimeTask b13 = new OneTimeTask("Onetime false",WORK,"13",LocalDateTime.of(2023,3,12,20,20));
        OneTimeTask bNow = new OneTimeTask("Onetime true",WORK,"14",LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth(),23,59));
        taskList.add(b);
        taskList.add(b1);
        taskList.add(b2);
        taskList.add(b3);
        taskList.add(b4);
        taskList.add(b5);
        taskList.add(b6);
        taskList.add(b7);
        taskList.add(b8);
        taskList.add(b9);
        taskList.add(b11);
        taskList.add(b12);
        taskList.add(b13);
        taskList.add(bNow);
    }
}
