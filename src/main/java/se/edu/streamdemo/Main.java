package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Task manager (using streams)");
        Datamanager dataManager = new Datamanager("./data/data.txt");
        // the . means current folder <- relative path
        ArrayList<Task> tasksData = dataManager.loadData();

        //System.out.println("Printing all data ...");
        //printAllData(tasksData);
        //printDataWithStreams(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);
        printDeadlinesUsingStream(tasksData);

        ArrayList<Task> filteredList = filterTasksByString(tasksData, "11");
        printAllData(filteredList);

        System.out.println("Total number of deadlines (iteration): "
                + countDeadlines(tasksData));
        System.out.println("Total number of deadlines (streams): "
                + countDeadlinesUsingStream(tasksData));
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

    public static void printAllData(ArrayList<Task> tasksData) {
        System.out.println("Printing data with iteration");
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDataWithStreams(ArrayList<Task> tasksData) {
        System.out.println("Printing data with streams");
        tasksData.stream()                     // create a stream
                .forEach(System.out::println); // terminal operator, will start lazy operation
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        System.out.println("Printing deadlines with iteration");
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStream(ArrayList<Task> tasksData) {
        tasksData.stream()
                    .filter((t) -> t instanceof Deadline)
                    .sorted((t1, t2) -> t1.getDescription().compareToIgnoreCase(t2.getDescription()))
                    .forEach(System.out::println);
    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasksData, String filterString) {
        ArrayList<Task> filteredList = (ArrayList<Task>) tasksData.stream()
                    .filter((t) -> t.getDescription().contains(filterString))
                    .collect(toList());
        return filteredList;
    }

    public static int countDeadlinesUsingStream(ArrayList<Task> tasksData) {
        int count = (int) tasksData.stream()
                .filter((t) -> t instanceof Deadline) //lambda function, for every t we are encountering
                .count(); // terminal operation; aggregate operation
        return count;
    }
}
