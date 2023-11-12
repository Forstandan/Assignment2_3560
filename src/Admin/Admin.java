package Admin;

import UI.AdminUI;
import User.UserGroup;
import User.User;
import Visitor.Visitor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin implements Visitor {
    private UserGroup root;
    private static Admin instance;
    // name, ID
    Map<String, User> userMap;
    Map<String, UserGroup> groupMap;
    int totalTweets;

    private Admin() {
        userMap = new HashMap<>();
        groupMap = new HashMap<>();
        totalTweets = 0;
        root = new UserGroup("root", null);
        AdminUI instance = AdminUI.getInstance(root);
    }

    public static Admin getInstance() {
        if (instance == null) {
            synchronized (Admin.class) {
                if (instance == null) {
                    instance = new Admin();
                }
            }
        }

        return instance;
    }

    public void createUser() {
        Scanner scanner = new Scanner(System.in);
        boolean containsKey;
        String username;
        User newUser;

        do {
            System.out.println("Please enter a new username");
            username = scanner.nextLine();

            containsKey = userMap.containsKey(username);
            if (containsKey) {
                System.out.println("Username already taken");
            }
        }
        while (containsKey);

        newUser = new User(username, root);
        userMap.put(username, newUser);

        root.addMember(newUser);
    }

    public void createGroup(UserGroup parentGroup) {
        Scanner scanner = new Scanner(System.in);
        boolean containsKey;
        String groupName;
        UserGroup newGroup;

        do {
            System.out.println("Please enter a new username");
            groupName = scanner.nextLine();

            containsKey = groupMap.containsKey(groupName);
            if (containsKey) {
                System.out.println("Username already taken");
            }
        }
        while (containsKey);

        newGroup = new UserGroup(groupName, parentGroup);
        groupMap.put(groupName, newGroup);

        parentGroup.addMember(newGroup);
    }

    // visitor pattern
    public void visitUser(User user) {
        totalTweets += user.getTweets().size();
    }
    // visitor pattern end
}
