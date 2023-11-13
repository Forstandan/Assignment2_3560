package Admin;

import UI.AdminUI;
import User.UserGroup;
import User.User;
import Visitor.Visitor;

import java.util.*;

public class Admin implements Visitor {
    private UserGroup root;
    private static Admin instance;
    // name, ID
    private Set<User> userSet;
    private Set<UserGroup> groupSet;
    int totalTweets;

    private Admin() {
        userSet = new HashSet<>();
        groupSet = new HashSet<>();
        totalTweets = 0;
        root = new UserGroup("root", null);
        groupSet.add(root);
        AdminUI instance = AdminUI.getInstance();
        instance.setRoot(root);
        instance.createAdminView();
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

    public User createUser(UserGroup parentGroup) {
        User newUser = new User("username", root);
        userSet.add(newUser);

        parentGroup.addMember(newUser);

        return newUser;
    }

    public void setUsername(User user, String username) {
        user.setUsername(username);
    }

    public UserGroup createGroup(UserGroup parentGroup) {
        UserGroup newGroup = new UserGroup("groupName", parentGroup);
        groupSet.add(newGroup);

        parentGroup.addMember(newGroup);

        return newGroup;
    }

    public void setGroupName(UserGroup group, String groupName) {
        group.setGroupName(groupName);
    }

    // visitor pattern
    public void visitUser(User user) {
        totalTweets += user.getTweets().size();
    }
    // visitor pattern end
}
