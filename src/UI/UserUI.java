package UI;

import Follow.Observer;
import Follow.Subject;
import User.*;
import Admin.Admin;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserUI extends UserUIListener implements Observer, Subject {
    private JLabel lastUpdated;
    private final List<Observer> observerList = new ArrayList<>();

    public User getCurrentUser() {
        return super.getCurrentUser();
    }

    private static DefaultListModel<String> updateFollowerList(User user, DefaultListModel<String> listModel) {
        listModel.clear();

        for (User user1 : user.getFollowing()) {
            if (user != user1) {
                listModel.addElement(user1.getID());
            }
        }
        return listModel;
    }

    private static DefaultListModel<String> updateFeedList(User user, DefaultListModel<String> listModel) {
        listModel.clear();

        if (user.getFeed() != null) {
            for (String tweet : user.getFeed()) {
                listModel.addElement(tweet);
            }
        }

        return listModel;
    }

    public void update(Subject subject) {
        List<String> updatedFeed = ((User) subject).getFeed();
        notifyObservers();
        setLastUpdated();
        getFeedListModel().insertElementAt(updatedFeed.get(updatedFeed.size() - 1), getFeedList().getSelectedIndex() + 1);
    }

    public void createUserUI() {
        User currentUser = (User) AdminUI.getCurrentComponent();
        attachObserver(Admin.getInstance());
        currentUser.setIsOpen(true, this);
        setCurrentUser(currentUser);

        /* creating panels */
        GridBagLayout gridBagLayout = new GridBagLayout();

        JPanel upperPanel = new JPanel();
        upperPanel.setBackground(Color.red);
        upperPanel.setBounds(0, 0, 380, 150);
        upperPanel.setLayout(new BorderLayout());

        JPanel followerPanel = new JPanel();
        followerPanel.setBackground(Color.red);
        followerPanel.setBounds(0, 150, 380, 100);
        followerPanel.setLayout(new BorderLayout());

        JPanel lowerPanel = new JPanel();
        lowerPanel.setBackground(Color.blue);
        lowerPanel.setBounds(0, 250, 380, 160);
        lowerPanel.setLayout(new BorderLayout());

        JPanel latestUpdatedPanel = new JPanel();
        latestUpdatedPanel.setBackground(Color.LIGHT_GRAY);
        latestUpdatedPanel.setBounds(0, 410, 380, 30);
        latestUpdatedPanel.setLayout(gridBagLayout);

        JPanel upperWidgetPanel = new JPanel();
        upperWidgetPanel.setBackground(Color.LIGHT_GRAY);
        upperWidgetPanel.setPreferredSize(new Dimension(300, 60));
        upperWidgetPanel.setLayout(gridBagLayout);

        JPanel lowerWidgetPanel = new JPanel();
        lowerWidgetPanel.setBackground(Color.LIGHT_GRAY);
        lowerWidgetPanel.setPreferredSize(new Dimension(750, 60));
        lowerWidgetPanel.setLayout(gridBagLayout);

        JPanel userIDTextPanel = new JPanel();
        userIDTextPanel.setBackground(Color.GRAY);
        userIDTextPanel.setLayout(gridBagLayout);
        userIDTextPanel.setPreferredSize(new Dimension(300, 60));

        JPanel currentUserPanel = new JPanel();
        currentUserPanel.setBackground(Color.DARK_GRAY);
        currentUserPanel.setLayout(gridBagLayout);
        currentUserPanel.setBounds(0, 0, 200, 100);
        currentUserPanel.setLayout(gridBagLayout);

        /* create components */
        JTextField userID = new JTextField();
        userID.setEditable(true);
        setUserID(userID);

        JLabel currentUserID = new JLabel();
        currentUserID.setText("@" + currentUser.getID());
        currentUserID.setFont(new Font("Arial", Font.ITALIC, 30));
        currentUserID.setForeground(Color.WHITE);

        lastUpdated = new JLabel();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd MMMM, yyyy");
        lastUpdated.setText("Last updated " + format.format(currentUser.getUpdateTime()));
        lastUpdated.setFont(new Font("Arial", Font.PLAIN, 10));
        lastUpdated.setForeground(Color.white);

        JLabel userCreationTime = new JLabel();
        format = new SimpleDateFormat("dd/MM/yy");
        userCreationTime.setText("Account created on " + format.format(new Date(currentUser.getCreationTime())));
        userCreationTime.setFont(new Font("Arial", Font.ITALIC, 10));
        userCreationTime.setForeground(Color.WHITE);

        JButton followUserButton = new JButton("Follow User");
        followUserButton.addActionListener(actFollowUser);
        followUserButton.setBorderPainted(false);
        followUserButton.setBackground(Color.gray);
        followUserButton.setForeground(Color.WHITE);

        JButton postTweetButton = new JButton("Post Tweet");
        postTweetButton.addActionListener(actTweet);
        postTweetButton.setBorderPainted(false);
        postTweetButton.setBackground(Color.gray);
        postTweetButton.setForeground(Color.WHITE);

        JTextField tweet = new JTextField();
        tweet.setEditable(true);
        setTweet(tweet);

        DefaultListModel<String> followerListModel = updateFollowerList(currentUser, new DefaultListModel<>());
        JList<String> followerList = new JList<>(followerListModel);
        followerList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        followerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        setFollowerList(followerList);
        setFollowerListModel(followerListModel);

        DefaultListModel<String> feedListModel = updateFeedList(currentUser, new DefaultListModel<>());
        JList<String> feedList = new JList<>(feedListModel);
        feedList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setFeedList(feedList);
        setFeedListModel(feedListModel);

        /* create frame and add components */
        JFrame frame = new JFrame("user view");
        frame.setBounds(500, 0, 390, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(null);

        gridBagLayout.setConstraints(followUserButton, constraintsFollowUserButton);
        gridBagLayout.setConstraints(userID, constraintsUserID);
        gridBagLayout.setConstraints(tweet, constraintsTweetTextField);
        gridBagLayout.setConstraints(postTweetButton, constraintsPostTweet);
        gridBagLayout.setConstraints(userCreationTime, constraintsUserCreationTime);

        upperWidgetPanel.add(userID);
        upperWidgetPanel.add(followUserButton);

        lowerWidgetPanel.add(tweet);
        lowerWidgetPanel.add(postTweetButton);

        currentUserPanel.add(currentUserID);
        currentUserPanel.add(userCreationTime);

        latestUpdatedPanel.add(lastUpdated);

        followerPanel.add(followerList);

        upperPanel.add(currentUserPanel);
        upperPanel.add(upperWidgetPanel, BorderLayout.SOUTH);
        lowerPanel.add(lowerWidgetPanel, BorderLayout.NORTH);
        lowerPanel.add(feedList);

        frame.add(upperPanel);
        frame.add(followerPanel);
        frame.add(lowerPanel);
        frame.add(latestUpdatedPanel);
    }

    public void attachObserver(Observer observer) {
        observerList.add(observer);
    }

    public void detachObserver(Observer observer) {
        observerList.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observerList) {
            observer.update(this);
        }
    }

    private void setLastUpdated() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss dd MMMM, yyyy");
        lastUpdated.setText("Last updated: " + format.format(getCurrentUser().getUpdateTime()));
    }
}


