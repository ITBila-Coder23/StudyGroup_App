package model;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;

public class StudyGroup implements Serializable {
    private String groupName;
    private List<User> members;
    private List<Message> messages;
    private List<Task> tasks;

    public StudyGroup(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
