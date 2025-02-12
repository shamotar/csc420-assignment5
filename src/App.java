/*
 *  Title: Assignment 5 - Time slices with AVL Tree
 *  Created by: Robert Norlander
 *  Email: shamotar@csp.edu
 *  Date: 2025-02-09
 *  Class: CSC 420 - Data Structures and Algorithms
 *  Professor: Susan Furtney
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Created and submitted by: Robert Norlander - shamotar@csp.edu");
        System.out.println("I certify that this is my own work\n");

        // Create AVL tree and list of completed processes
        AVLTree<ProcessInfo> processTree = new AVLTree<>();
        List<ProcessInfo> completedProcesses = new ArrayList<>();

        // Read processes from file
        System.out.println("Reading processes from file... >");
        readProcessesFromFile("src/static/processListMaster.txt", processTree);

        // Print tree by level
        System.out.println("\nTree Structure... >");
        processTree.printByLevel();

        // Execute processes
        System.out.println("\nExecuting processes... >");
        executeProcesses(processTree, completedProcesses);

        // Display results
        System.out.println("\nResults... >");
        for (ProcessInfo process : completedProcesses) {
            System.out.println(process.displayCompletedInfo());
        }
    }

    /**
     * Read processes from file and insert into AVL tree
     * @param filename
     * @param processTree
     */
    public static void readProcessesFromFile(String filename, AVLTree<ProcessInfo> processTree) {
        // Read process list from file
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] processInfo = data.split("\\|");
                ProcessInfo process = new ProcessInfo(processInfo[0], Integer.parseInt(processInfo[1]),
                        Integer.parseInt(processInfo[2]), Integer.parseInt(processInfo[3]));
                try {
                    processTree.insert(process);
                    System.out.println("Adding:\t" + process);
                } catch (Exception e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Execute processes in the AVL tree
     * @param processTree AVL tree of processes
     * @param completedProcesses List of completed processes
     * @return void
     */
    public static void executeProcesses(AVLTree<ProcessInfo> processTree, List<ProcessInfo> completedProcesses) {
        // Process execution loop
        while (true) {
            List<ProcessInfo> currentProcesses = new ArrayList<>();
            processTree.inorder(currentProcesses);

            if (currentProcesses.isEmpty()) {
                break;
            }

            for (ProcessInfo process : currentProcesses) {
                int timeSlice = 10 - process.getProcessPriority();
                boolean completed = process.executeProcess(timeSlice);

                if (completed) {
                    System.out.println("Process has completed: " + process.getProcessName() +
                            " - " + process.getProcessId());
                    completedProcesses.add(process);
                    processTree.delete(process);
                }
            }
        }
    }
}
