package com.indiasatcom.taskmanagement.enums;

public enum Constants {
    NOT_FOUND("Could not find the task with id: "),
    TASK_REMOVED("Task Removed Successfully");

    private final String value;

    private Constants(String value){
        this.value = value;
    }

    public String toString(){
        return this.value;
    }

}
