package se.edu.streamdemo;

import se.edu.streamdemo.data.Datamanager;
import se.edu.streamdemo.task.Deadline;
import se.edu.streamdemo.task.Task;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Task manager (using streams)");
        Datamanager dataManager = new Datamanager("./data/data.txt");
        // the . means current folder <- relative path
        ArrayList<Task> tasksData = dataManager.loadData();

        System.out.println("Printing all data ...");
        printAllData(tasksData);

        System.out.println("Printing deadlines ...");
        printDeadlines(tasksData);

        System.out.println("Total number of deadlines: " + countDeadlines(tasksData));

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
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesUsingStream(ArrayList<Task> tasksData) {
        System.out.println("Printing deadlines with streams");
        tasksData.stream()
                    .filter((t) -> t instanceof Deadline) //lambda function, for every t we are encountering
                    .forEach(System.out::println);
    }
    public static int countDeadlinesUsingStream(ArrayList<Task> tasksData) {
        int count = (int) tasksData.stream()
                .filter((t) -> t instanceof Deadline) //lambda function, for every t we are encountering
                .count(); // terminal operation; aggregate operation
        return count;
    }
}
