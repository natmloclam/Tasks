package se.edu.streamdemo;

import static java.util.stream.Collectors.toList;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        printWelcomeMessage();
        Datamanager dataManager = new Datamanager("./data/data.txt"); // relative path
        // relative path starts from where you run the program, not where the jar file is
        // use relative as much as possible - least likely to cause programs across different machines
        // "C:\\Users\\MALCOLM\\Documents\\CS2113\\ip\\data\\data.txt" <<< absolute path
        // /home/username/ip/data/data.txt <<< absolute path on linux
        ArrayList<Task> tasksData = dataManager.loadData();


        System.out.println("Printing deadlines ...");
//        printDeadlines(tasksData);
        printDeadlines(tasksData);
        printDeadlinesUsingStreams(tasksData);

        System.out.println("Total number of deadlines (iteration): " + countDeadlines(tasksData));
        System.out.println("Total number of deadlines (streams): " + countDeadlineUsingStreams(tasksData));


        System.out.println("Printing filtered list ...");
        ArrayList<Task> filteredList = filterList(tasksData, "11");
        printAllData(filteredList);
    }

    private static void printWelcomeMessage() {
        System.out.println("Welcome to Task manager (using streams)");
    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlineUsingStreams(ArrayList<Task> tasks) {
        int count = (int) tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .count();

        return count;
    }
    public static void printAllData(ArrayList<Task> tasksData) {
        System.out.println("Printing data using iteration ...");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataUsingStreams(ArrayList<Task> tasks) {
        System.out.println("Printing data using streams ...");
        tasks.stream()
                .forEach(System.out::println);
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStreams(ArrayList<Task> tasks) {
        System.out.println("Printing deadlines using streams ...");
        tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .sorted((t1, t2) -> t1.getDescription().compareTo(t2.getDescription()))
                .forEach(System.out::println);
    }

    public static ArrayList<Task> filterList(ArrayList<Task> tasks, String filterString) {
        ArrayList<Task> filteredList = (ArrayList<Task>) tasks.stream() // convert source into stream
                // call lambda to extract description and checks if it contains filterString
                .filter((t) -> t.getDescription().contains(filterString))
                .collect(toList());
        return filteredList;
    }

}