package model;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String description;
    private Date dueDate;
    private boolean completed;

    public Task(String description, Date dueDate) {
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return description + " (Due: " + dueDate + ", Completed: " + completed + ")";
    }
}
