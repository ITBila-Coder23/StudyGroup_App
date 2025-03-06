package service;

import model.Message;
import model.StudyGroup;
import model.User;
import storage.Database;

public class ChatService {
    private Database database;

    public ChatService(Database database) {
        this.database = database;
    }

    /**
     * Sends a message to a study group.
     * @param groupName the name of the study group
     * @param user the user sending the message
     * @param content the content of the message
     */
    public void sendMessage(String groupName, User user, String content) {
        try {
            StudyGroup studyGroup = database.getStudyGroupByName(groupName);
            if (studyGroup != null) {
                Message message = new Message(content, user);
                studyGroup.getMessages().add((com.sun.corba.se.impl.protocol.giopmsgheaders.Message) message);
                database.saveStudyGroup(studyGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
