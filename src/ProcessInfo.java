/*
 *  Title: Assignment 5 - Time slices with AVL Tree
 *  Created by: Robert Norlander
 *  Email: shamotar@csp.edu
 *  Date: 2025-02-09
 *  Class: CSC 420 - Data Structures and Algorithms
 *  Professor: Susan Furtney
 */

class ProcessInfo implements Comparable<ProcessInfo> {
    private String processName;
    private int processId;
    private int processPriority;
    private int processRemainingRuntime;
    private long processStartTime;
    private long processEndTime;
    private long processElapsedTime;

    public ProcessInfo(String name, int id, int priority, int runtime) {
        this.processName = name;
        this.processId = id;
        this.processPriority = priority;
        this.processRemainingRuntime = runtime;
        this.processStartTime = 0;
        this.processEndTime = 0;
        this.processElapsedTime = 0;
    }

    /**
     * Execute process for specified time
     * @param milliseconds
     * @return boolean value for process completion
     */
    public boolean executeProcess(int milliseconds) {
        if (processStartTime == 0) {
            processStartTime = System.currentTimeMillis();
        }

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        processRemainingRuntime -= milliseconds;

        if (processRemainingRuntime <= 0) {
            endProcess();
            return true;
        }
        return false;
    }

    /**
     * End process and calculate elapsed time
     * Called when process is completed
     */
    private void endProcess() {
        processEndTime = System.currentTimeMillis();
        processElapsedTime = processEndTime - processStartTime;
    }

    /**
     * Display process information after completion
     * @return String with process information
     */
    public String displayCompletedInfo() {
        return String.format("Process Name: %s\tProcess Priority: %d\tCompletion Time: %d",
                processName, processPriority, processElapsedTime);
    }

    /**
     * Compare process priority and ID
     * @param other ProcessInfo object to compare
     * @return int value for comparison
     */
    @Override
    public int compareTo(ProcessInfo other) {
        // Primary sort by priority
        int priorityCompare = Integer.compare(this.processPriority, other.processPriority);
        if (priorityCompare != 0) {
            return priorityCompare;
        }
        // Secondary sort by process ID to handle same priority
        return Integer.compare(this.processId, other.processId);
    }

    /**
     * Display process information
     * @return String with process information
     */
    @Override
    public String toString() {
        return String.format("Process Name: %s\tProcess Id: %d\tProcess Priority: %d\tProcess Remaining Runtime: %d",
                processName, processId, processPriority, processRemainingRuntime);
    }

    // Getters and setters
    public String getProcessName() {
        return processName;
    }

    public int getProcessId() {
        return processId;
    }

    public int getProcessPriority() {
        return processPriority;
    }

    public int getProcessRemainingRuntime() {
        return processRemainingRuntime;
    }
}