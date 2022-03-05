package de.xitaso.taskman.entities;

public enum TaskState {
    ToDo, Waiting, Done, Canceled;

    public static boolean IsOpen(TaskState state) {
        return state != Done && state != Canceled;
    }

    public static boolean IsClosed(TaskState state) {
        return !IsOpen(state);
    }
}
