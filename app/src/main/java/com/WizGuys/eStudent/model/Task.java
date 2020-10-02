package com.WizGuys.eStudent.model;

import com.WizGuys.eStudent.helperClass.Common;

public class Task {

    private String taskKey;
    private String task;
    private String date;
    private String state;
    private String userEmail;

    public Task() {

    }

    public Task(String task, String date, String userEmail) {
        this.task = task;
        this.date = date;
        this.userEmail = userEmail;
        this.state = Common.TASK_UNFINISHED;
    }

    public Task(String taskKey, String task, String date, String state, String userEmail) {
        this.taskKey = taskKey;
        this.task = task;
        this.date = date;
        this.state = state;
        this.userEmail = userEmail;
    }

    public String getTaskKey() {
        return taskKey;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
