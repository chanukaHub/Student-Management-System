package com.WizGuys.eStudent.model;

import com.WizGuys.eStudent.helperClass.Common;

public class Task {
    private String task;
    private String date;
    private String state;

    public Task(String task) {
        this.task = task;
    }

    //for insert and update operations
    public Task(String task, String date) {
        this.task = task;
        this.date = date;
        this.state = Common.TASK_UNFINISHED;
    }

    public Task(String task, String date, String state) {
        this.task = task;
        this.date = date;
        this.state = state;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setState(String state) {
        this.state = state;
    }
}
