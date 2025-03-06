package service;

import model.StudyGroup;
import model.User;
import storage.Database;

import java.util.List;

public class StudyGroupService {
    private Database database;

    public StudyGroupService(Database database) {
        this.database = database;
    }

    /**
     * Creates a new study group with the given group name.
     * @param groupName the name of the study group
     * @return the created StudyGroup object
     */
    public StudyGroup createStudyGroup(String groupName) {
        try {
            StudyGroup studyGroup = new StudyGroup(groupName);
            database.saveStudyGroup(studyGroup);
            return studyGroup;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adds a user to an existing study group.
     * @param groupName the name of the study group
     * @param user the user to be added
     * @return true if the user is successfully added, false otherwise
     */
    public boolean addUserToStudyGroup(String groupName, User user) {
        try {
            StudyGroup studyGroup = database.getStudyGroupByName(groupName);
            if (studyGroup != null) {
                studyGroup.getMembers().add(user);
                database.saveStudyGroup(studyGroup);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes a user from an existing study group.
     * @param groupName the name of the study group
     * @param user the user to be removed
     * @return true if the user is successfully removed, false otherwise
     */
    public boolean removeUserFromStudyGroup(String groupName, User user) {
        try {
            StudyGroup studyGroup = database.getStudyGroupByName(groupName);
            if (studyGroup != null) {
                studyGroup.getMembers().remove(user);
                database.saveStudyGroup(studyGroup);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lists all study groups a user is part of.
     * @param username the username of the user
     * @return a list of StudyGroup objects the user is part of
     */
    public List<StudyGroup> listUserGroups(String username) {
        try {
            return database.getUserGroups(username);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
