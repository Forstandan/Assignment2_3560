package UI;

import Follow.Observer;
import Follow.Subject;
import User.*;
import Admin.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserUI implements Observer {
    private JTextField userID;
    private JTextField tweet;
    private JList<String> followerList;
    private DefaultListModel<String> followerListModel;
    private JList<String> feedList;
    private DefaultListModel<String> feedListModel;
    private User currentUser;
    private final static GridBagConstraints constraintsUserID = new GridBagConstraints(
            0, 0, 100, 100, 1, 1,
            GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(0, 10, 0, 130), 0, 0
    );

    private final static GridBagConstraints constraintsFollowUserButton = new GridBagConstraints(
            0, 0, 1, 1,  1, 1,
            GridBagConstraints.LINE_END, GridBagConstraints.NONE,
            new Insets(15, 0, 15, 10), 0, 0
    );

    private final static GridBagConstraints constraintsTweetTextField = new GridBagConstraints(
            0, 0, 1, 1, 1, 1,
            GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL,
            new Insets(20, 10, 20, 130), 0, 0
    );

    private final static GridBagConstraints constraintsPostTweet = new GridBagConstraints(
            0, 0, 1, 1,  1, 1,
            GridBagConstraints.LINE_END, GridBagConstraints.NONE,
            new Insets(15, 0, 15, 10), 0, 0
    );

    private final ActionListener actFollowUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {

            if (userID.getText().equals("")) {
                UserComponent currentComponent = AdminUI.getInstance().getCurrentComponent();
                if (currentComponent instanceof User && currentComponent != currentUser) {
                    currentUser.follow((User) currentComponent);
                    followerListModel.insertElementAt(currentComponent.getID(), followerList.getSelectedIndex() + 1);
                    userID.setText("");
                }
            }
            else {
                Set<User> userSet = Admin.getInstance().getUserSet();

                for (User user : userSet) {
                    if (Objects.equals(user.getID(), userID.getText()) && !currentUser.getFollowing().contains(user)) {
                        currentUser.follow(user);
                        followerListModel.insertElementAt(user.getID(), followerList.getSelectedIndex() + 1);
                        userID.setText("");
                        break;
                    }
                }
            }
        }
    };

    private final ActionListener actTweet = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonActionPerformed(e);
        }

        private void buttonActionPerformed(ActionEvent e) {
            if (!tweet.getText().equals("")) {
                List<String> tweets = currentUser.getTweets();
                tweets.add(tweet.getText());
                currentUser.setTweets(tweets);
                currentUser.notifyObservers();
                tweet.setText("");
            }
        }
    };

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
        feedListModel.insertElementAt(updatedFeed.get(updatedFeed.size() - 1), feedList.getSelectedIndex() + 1);
    }

    public void createUserUI() {
        currentUser = (User) AdminUI.getInstance().getCurrentComponent();
        currentUser.setIsOpen(true, this);

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

        /* create components */
        userID = new JTextField();
        userID.setEditable(true);

        JLabel currentUserID = new JLabel();
        currentUserID.setText("@" + currentUser.getID());
        currentUserID.setFont(new Font("Arial", Font.ITALIC, 30));
        currentUserID.setForeground(Color.WHITE);

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

        tweet = new JTextField();
        tweet.setEditable(true);

        followerListModel = updateFollowerList(currentUser, new DefaultListModel<>());
        followerList = new JList<>(followerListModel);
        followerList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        followerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        feedListModel = updateFeedList(currentUser, new DefaultListModel<>());
        feedList = new JList<>(feedListModel);
        feedList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        /* create frame and add components */
        JFrame frame = new JFrame("user view");
        frame.setBounds(500, 0, 390, 450);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(null);

        gridBagLayout.setConstraints(followUserButton, constraintsFollowUserButton);
        gridBagLayout.setConstraints(userID, constraintsUserID);
        gridBagLayout.setConstraints(tweet, constraintsTweetTextField);
        gridBagLayout.setConstraints(postTweetButton, constraintsPostTweet);

        upperWidgetPanel.add(userID);
        upperWidgetPanel.add(followUserButton);

        lowerWidgetPanel.add(tweet);
        lowerWidgetPanel.add(postTweetButton);

        currentUserPanel.add(currentUserID);

        followerPanel.add(followerList);

        upperPanel.add(currentUserPanel);
        upperPanel.add(upperWidgetPanel, BorderLayout.SOUTH);
        lowerPanel.add(lowerWidgetPanel, BorderLayout.NORTH);
        lowerPanel.add(feedList);

        frame.add(upperPanel);
        frame.add(followerPanel);
        frame.add(lowerPanel);
    }
}


