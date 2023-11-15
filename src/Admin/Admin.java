package Admin;

import Follow.Observer;
import Follow.Subject;
import UI.AdminUI;
import User.UserGroup;
import User.User;
import Visitor.Visitor;

import java.util.*;

public class Admin implements Visitor, Observer {
    private UserGroup root;
    private static Admin instance;
    // name, ID
    private Set<User> userSet;
    private Set<UserGroup> groupSet;
    private int totalTweets;
    private int positiveTweetTotal;

    private Admin() {
        userSet = new HashSet<>();
        groupSet = new HashSet<>();
        totalTweets = 0;
        positiveTweetTotal = 0;
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

    public int getTotalTweets() {
        return totalTweets;
    }

    public int getPositiveTweetTotal() {
        return positiveTweetTotal;
    }
    public int getNumOfUsers() {
        return userSet.size();
    }

    public int getNumOfGroups() {
        return groupSet.size();
    }

    public Set<User> getUserSet() { return userSet; }

    public User createUser(UserGroup parentGroup, String username) {
        if (!nameIsTaken(username)) {
            User newUser = new User(username, parentGroup);
            userSet.add(newUser);

            parentGroup.addMember(newUser);

            newUser.attachObserver(this);

            return newUser;
        }

        return null;
    }

    public void setUsername(User user, String username) {
        if (!nameIsTaken(username)) {
            user.setID(username);
        }
    }

    public boolean nameIsTaken(String username) {
        for (User user: userSet) {
            if (user.getID().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public UserGroup createGroup(UserGroup parentGroup, String groupname) {
        UserGroup newGroup = new UserGroup(groupname, parentGroup);
        groupSet.add(newGroup);

        parentGroup.addMember(newGroup);

        return newGroup;
    }

    public void setGroupName(UserGroup group, String groupName) {
        group.setGroupName(groupName);
    }

    // visitor pattern
    public void visitUser(User user) {
        List<String> tweets = user.getTweets();
        String latestTweet = tweets.get(tweets.size() - 1);
        if (isPositiveMessage(latestTweet)) {
            positiveTweetTotal++;
        }
    }
    // visitor pattern end

    // Observer pattern
    public void update(Subject subject) {
        if (subject instanceof User) {
            totalTweets++;
            ((User) subject).accept(this);
        }
    }
    // Observer pattern end

    private boolean isPositiveMessage(String tweet) {
        return tweet.contains("good") || tweet.contains("happy") || tweet.contains("great") || tweet.contains("positive");
    }
}
