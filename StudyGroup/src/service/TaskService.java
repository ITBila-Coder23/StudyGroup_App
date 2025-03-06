package service;

import model.StudyGroup;
import model.Task;
import storage.Database;

import java.util.Date;
import java.util.List;

public class TaskService {
    private Database database;

    public TaskService(Database database) {
        this.database = database;
    }

    /**
     * Adds a task to a study group.
     * @param groupName the name of the study group
     * @param description the description of the task
     * @param dueDate the due date of the task
     */
    public void addTask(String groupName, String description, Date dueDate) {
        try {
            StudyGroup studyGroup = database.getStudyGroupByName(groupName);
            if (studyGroup != null) {
                Task task = new Task(description, dueDate);
                studyGroup.getTasks().add(task);
                database.saveStudyGroup(studyGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Marks a task as completed in a study group.
     * @param groupName the name of the study group
     * @param description the description of the task
     * @return true if the task is successfully marked as completed, false otherwise
     */
    public boolean markTaskAsCompleted(String groupName, String description) {
        try {
            StudyGroup studyGroup = database.getStudyGroupByName(groupName);
            if (studyGroup != null) {
                for (Task task : studyGroup.getTasks()) {
                    if (task.getDescription().equals(description)) {
                        task.setCompleted(true);
                        database.saveStudyGroup(studyGroup);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Views all tasks in a study group.
     * @param groupName the name of the study group
     * @return a list of Task objects
     */
    public List<javafx.concurrent.Task> viewTasks(String groupName) {
        try {
            StudyGroup studyGroup = database.getStudyGroupByName(groupName);
            if (studyGroup != null) {
                return studyGroup.getTasks();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
