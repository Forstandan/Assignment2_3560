package UI;

import User.User;

import javax.swing.*;
import java.awt.*;

public class UserUIFields {
    private JTextField userID;
    private JTextField tweet;
    private JList<String> followerList;
    private DefaultListModel<String> followerListModel;
    private JList<String> feedList;
    private DefaultListModel<String> feedListModel;
    private User currentUser;

    public final static GridBagConstraints constraintsUserID = new GridBagConstraints(
            0, 0, 100, 100, 1, 1,
            GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 0, 130), 0, 0
    );

    public final static GridBagConstraints constraintsFollowUserButton = new GridBagConstraints(
            0, 0, 1, 1,  1, 1,
            GridBagConstraints.LINE_END, GridBagConstraints.NONE,
            new Insets(15, 0, 15, 10), 0, 0
    );

    public final static GridBagConstraints constraintsTweetTextField = new GridBagConstraints(
            0, 0, 1, 1, 1, 1,
            GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(20, 10, 20, 130), 0, 0
    );

    public final static GridBagConstraints constraintsPostTweet = new GridBagConstraints(
            0, 0, 1, 1,  1, 1,
            GridBagConstraints.LINE_END, GridBagConstraints.NONE,
            new Insets(15, 0, 15, 10), 0, 0
    );

    public JTextField getUserID() {
        return userID;
    }

    public void setUserID(JTextField userID) {
        this.userID = userID;
    }

    public JTextField getTweet() {
        return tweet;
    }

    public void setTweet(JTextField tweet) {
        this.tweet = tweet;
    }

    public JList<String> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(JList<String> followerList) {
        this.followerList = followerList;
    }

    public DefaultListModel<String> getFollowerListModel() {
        return followerListModel;
    }

    public void setFollowerListModel(DefaultListModel<String> followerListModel) {
        this.followerListModel = followerListModel;
    }

    public JList<String> getFeedList() {
        return feedList;
    }

    public void setFeedList(JList<String> feedList) {
        this.feedList = feedList;
    }

    public DefaultListModel<String> getFeedListModel() {
        return feedListModel;
    }

    public void setFeedListModel(DefaultListModel<String> feedListModel) {
        this.feedListModel = feedListModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
