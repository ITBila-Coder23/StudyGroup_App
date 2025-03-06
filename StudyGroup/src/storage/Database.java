package storage;

import model.StudyGroup;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Database {
    private List<User> users;
    private List<StudyGroup> studyGroups;

    public Database() {
        loadDatabase();
    }

    private void loadDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.dat"))) {
            users = (List<User>) ois.readObject();
            studyGroups = (List<StudyGroup>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new ArrayList<>();
            studyGroups = new ArrayList<>();
        }
    }

    private void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(users);
            oos.writeObject(studyGroups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user) {
        users.add(user);
        saveDatabase();
    }

    public Optional<User> getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public void saveStudyGroup(StudyGroup studyGroup) {
        studyGroups.removeIf(group -> group.getGroupName().equals(studyGroup.getGroupName()));
        studyGroups.add(studyGroup);
        saveDatabase();
    }

    public StudyGroup getStudyGroupByName(String groupName) {
        return studyGroups.stream().filter(group -> group.getGroupName().equals(groupName)).findFirst().orElse(null);
    }

    public List<StudyGroup> getUserGroups(String username) {
        List<StudyGroup> userGroups = new ArrayList<>();
        for (StudyGroup group : studyGroups) {
            for (User member : group.getMembers()) {
                if (member.getUsername().equals(username)) {
                    userGroups.add(group);
                }
            }
        }
        return userGroups;
    }
}
