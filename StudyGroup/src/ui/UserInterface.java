package ui;

import model.User;
import service.ChatService;
import service.StudyGroupService;
import service.TaskService;
import service.UserService;
import storage.Database;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private UserService userService;
    private StudyGroupService studyGroupService;
    private ChatService chatService;
    private TaskService taskService;
    private Scanner scanner;

    public UserInterface() {
        Database database = new Database();
        this.userService = new UserService(database);
        this.studyGroupService = new StudyGroupService(database);
        this.chatService = new ChatService(database);
        this.taskService = new TaskService(database);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Welcome to the Virtual Study Group App!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }

        if (userService.registerUser(username, password)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. User may already exist.");
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password cannot be empty.");
            return;
        }

        User user = userService.loginUser(username, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + username + "!");
            userMenu(user);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void userMenu(User user) {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. Create Study Group");
            System.out.println("2. Join Study Group");
            System.out.println("3. Send Message");
            System.out.println("4. Add Task");
            System.out.println("5. View Groups");
            System.out.println("6. View Tasks");
            System.out.println("7. Mark Task as Completed");
            System.out.println("8. Remove User from Group");
            System.out.println("9. Logout");
            System.out.print("Please choose an option: ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        createStudyGroup();
                        break;
                    case 2:
                        joinStudyGroup(user);
                        break;
                    case 3:
                        sendMessage(user);
                        break;
                    case 4:
                        addTask();
                        break;
                    case 5:
                        listUserGroups(user);
                        break;
                    case 6:
                        viewTasks();
                        break;
                    case 7:
                        markTaskAsCompleted();
                        break;
                    case 8:
                        removeUserFromGroup(user);
                        break;
                    case 9:
                        System.out.println("Logging out. Goodbye, " + user.getUsername() + "!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void createStudyGroup() {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine().trim();

        if (groupName.isEmpty()) {
            System.out.println("Group name cannot be empty.");
            return;
        }

        if (studyGroupService.createStudyGroup(groupName) != null) {
            System.out.println("Study group '" + groupName + "' created successfully!");
        } else {
            System.out.println("Failed to create study group. Try a different name.");
        }
    }

    private void joinStudyGroup(User user) {
        System.out.print("Enter group name to join: ");
        String groupName = scanner.nextLine().trim();

        if (groupName.isEmpty()) {
            System.out.println("Group name cannot be empty.");
            return;
        }

        if (studyGroupService.addUserToStudyGroup(groupName, user)) {
            System.out.println("Successfully joined study group '" + groupName + "'!");
        } else {
            System.out.println("Failed to join study group. Group may not exist.");
        }
    }

    private void sendMessage(User user) {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine().trim();
        System.out.print("Enter message content: ");
        String content = scanner.nextLine().trim();

        if (groupName.isEmpty() || content.isEmpty()) {
            System.out.println("Group name and message content cannot be empty.");
            return;
        }

        chatService.sendMessage(groupName, user, content);
        System.out.println("Message sent to group '" + groupName + "'!");
    }

    private void addTask() {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine().trim();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter due date (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine().trim();

        if (groupName.isEmpty() || description.isEmpty() || dateStr.isEmpty()) {
            System.out.println("Group name, task description, and due date cannot be empty.");
            return;
        }

        try {
            Date dueDate = Date.valueOf(dateStr);
            taskService.addTask(groupName, description, dueDate);
            System.out.println("Task added to group '" + groupName + "'!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use yyyy-mm-dd.");
        }
    }

    private void listUserGroups(User user) {
        System.out.println("Groups you are part of:");
        List<String> groups = studyGroupService.listUserGroups(user.getUsername());
        if (groups.isEmpty()) {
            System.out.println("You are not part of any groups.");
        } else {
            groups.forEach(group -> System.out.println("- " + group));
        }
    }

    private void viewTasks() {
        System.out.print("Enter group name to view tasks: ");
        String groupName = scanner.nextLine().trim();

        if (groupName.isEmpty()) {
            System.out.println("Group name cannot be empty.");
            return;
        }

        List<Task> tasks = taskService.viewTask(groupName);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for group: " + groupName);
        } else {
            tasks.forEach(task -> System.out.println(
                    String.format("%s (Due: %s, Completed: %b)",
                            task.getDescription(),
                            task.getDueDate(),
                            task.isCompleted())));
        }
    }

    private void markTaskAsCompleted() {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine().trim();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine().trim();

        if (groupName.isEmpty() || description.isEmpty()) {
            System.out.println("Group name and task description cannot be empty.");
            return;
        }

        if (taskService.markTaskAsCompleted(groupName, description)) {
            System.out.println("Task marked as completed!");
        } else {
            System.out.println("Failed to mark task as completed. Task may not exist.");
        }
    }

    private void removeUserFromGroup(User user) {
        System.out.print("Enter group name: ");
        String groupName = scanner.nextLine().trim();
        System.out.print("Enter username to remove: ");
        String username = scanner.nextLine().trim();

        if (groupName.isEmpty() || username.isEmpty()) {
            System.out.println("Group name and username cannot be empty.");
            return;
        }

        if (studyGroupService.removeUserFromStudyGroup(groupName, username)) {
            System.out.println("User '" + username + "' removed from group '" + groupName + "'!");
        } else {
            System.out.println("Failed to remove user from group. Group or user may not exist.");
        }
    }
}